/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.bigtable.grpc;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import com.google.api.client.util.Strings;
import com.google.bigtable.admin.v2.ListClustersResponse;
import com.google.cloud.bigtable.config.BigtableOptions;
import com.google.cloud.bigtable.config.BigtableVersionInfo;
import com.google.cloud.bigtable.config.BulkOptions;
import com.google.cloud.bigtable.config.CredentialOptions;
import com.google.cloud.bigtable.config.Logger;
import com.google.cloud.bigtable.config.RetryOptions;
import com.google.cloud.bigtable.grpc.async.AsyncExecutor;
import com.google.cloud.bigtable.grpc.async.BulkMutation;
import com.google.cloud.bigtable.grpc.async.BulkRead;
import com.google.cloud.bigtable.grpc.async.ResourceLimiter;
import com.google.cloud.bigtable.grpc.io.ChannelPool;
import com.google.cloud.bigtable.grpc.io.CredentialInterceptorCache;
import com.google.cloud.bigtable.grpc.io.GoogleCloudResourcePrefixInterceptor;
import com.google.cloud.bigtable.metrics.BigtableClientMetrics;
import com.google.cloud.bigtable.metrics.BigtableClientMetrics.MetricLevel;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.DnsNameResolverProvider;
import io.grpc.internal.GrpcUtil;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.util.Recycler;

/**
 * <p>Encapsulates the creation of Bigtable Grpc services.</p>
 *
 * <p>The following functionality is handled by this class:
 * <ol>
 *   <li> Created Executors
 *   <li> Creates Channels - netty ChannelImpls, ReconnectingChannel and ChannelPools
 *   <li> Creates ChannelInterceptors - auth headers, performance interceptors.
 *   <li> Close anything above that needs to be closed (ExecutorService, CahnnelImpls)
 * </ol>
 *
 * @author sduskis
 * @version $Id: $Id
 */
public class BigtableSession implements Closeable {

  private static ChannelPool cachedDataChannelPool = null;
  private static final Logger LOG = new Logger(BigtableSession.class);
  private static ResourceLimiter resourceLimiter;
  private static SslContextBuilder sslBuilder;

  // 256 MB, server has 256 MB limit.
  private final static int MAX_MESSAGE_SIZE = 1 << 28;

  @VisibleForTesting
  static final String PROJECT_ID_EMPTY_OR_NULL = "ProjectId must not be empty or null.";
  @VisibleForTesting
  static final String INSTANCE_ID_EMPTY_OR_NULL = "InstanceId must not be empty or null.";
  @VisibleForTesting
  static final String USER_AGENT_EMPTY_OR_NULL = "UserAgent must not be empty or null";

  static {
    turnOffNettyRecycler();
    performWarmup();
  }

  /**
   * The netty {@link Recycler} has caused some problems for long running operations in some
   * versions of netty. As of this comment (10/21/2016), we are using netty 4.1.3.Final. The
   * Recycler uses a system property, "io.netty.recycler.maxCapacity" which needs to be set to "0"
   * to turn off potentially problematic behavior. The string gets transformed via the shading
   * process, and ends up being similar to the Recycler's package name. This method sets the value
   * to "0" if the value is not set.
   */
  @VisibleForTesting
  static void turnOffNettyRecycler() {
    String packageName = Recycler.class.getName();
    String prefix = packageName.substring(0, packageName.indexOf(".util.Recycler"));
    final String key = prefix + ".recycler.maxCapacity";
    LOG.debug("Using prefix %s for io.netty.", prefix);
    if (System.getProperty(key) == null) {
      System.setProperty(key, "0");
    }
  }

  private synchronized static SslContext createSslContext() throws SSLException {
    if (sslBuilder == null) {
      sslBuilder = GrpcSslContexts.forClient();
    }
    return sslBuilder.build();
  }

  private static void performWarmup() {
    // Initialize some core dependencies in parallel.  This can speed up startup by 150+ ms.
    ExecutorService connectionStartupExecutor = Executors
        .newCachedThreadPool(GrpcUtil.getThreadFactory("BigtableSession-startup-%d", true));

    connectionStartupExecutor.execute(new Runnable() {
      @Override
      public void run() {
        // The first invocation of BigtableSessionSharedThreadPools.getInstance() is expensive.
        // Reference it so that it gets constructed asynchronously.
        BigtableSessionSharedThreadPools.getInstance();
      }
    });
    connectionStartupExecutor.execute(new Runnable() {
      @Override
      public void run() {
        // The first invocation of createSslContext() is expensive.
        // Create a throw away object in order to speed up the creation of the first
        // BigtableConnection which uses SslContexts under the covers.
        try {
          // We create multiple channels via refreshing and pooling channel implementation.
          // Each one needs its own SslContext.
          createSslContext();
        } catch (SSLException e) {
          // ignore.  This doesn't happen frequently, but even if it does, it's inconsequential.
        }
      }
    });
    for (final String host : Arrays.asList(BigtableOptions.BIGTABLE_DATA_HOST_DEFAULT,
      BigtableOptions.BIGTABLE_TABLE_ADMIN_HOST_DEFAULT)) {
      connectionStartupExecutor.execute(new Runnable() {
        @Override
        public void run() {
          // The first invocation of InetAddress retrieval is expensive.
          // Reference it so that it gets constructed asynchronously.
          try {
            InetAddress.getByName(host);
          } catch (UnknownHostException e) {
            // ignore.  This doesn't happen frequently, but even if it does, it's inconsequential.
          }
        }
      });
    }
    connectionStartupExecutor.shutdown();
  }

  private synchronized static void initializeResourceLimiter(BigtableOptions options) {
    if (resourceLimiter == null) {
      int maxInflightRpcs = options.getBulkOptions().getMaxInflightRpcs();
      long maxMemory = options.getBulkOptions().getMaxMemory();
      resourceLimiter = new ResourceLimiter(maxMemory, maxInflightRpcs);
    }
  }

  private final BigtableDataClient dataClient;
  private BigtableTableAdminClient tableAdminClient;
  private BigtableInstanceGrpcClient instanceAdminClient;

  private final BigtableOptions options;
  private final List<ManagedChannel> managedChannels = Collections
      .synchronizedList(new ArrayList<ManagedChannel>());
  private final ClientInterceptor[] headerInterceptors;

  /**
   * This cluster name is either configured via BigtableOptions' clusterId, or via a lookup of the
   * clusterID based on BigtableOptions projectId and instanceId.  See {@link BigtableClusterUtilities}
   */
  private BigtableClusterName clusterName;

  /**
   * <p>Constructor for BigtableSession.</p>
   *
   * @param opts a {@link com.google.cloud.bigtable.config.BigtableOptions} object.
   * @throws java.io.IOException if any.
   */
  public BigtableSession(BigtableOptions opts) throws IOException {
    this.options = resolveLegacyOptions(opts);

    Preconditions.checkArgument(
        !Strings.isNullOrEmpty(options.getProjectId()), PROJECT_ID_EMPTY_OR_NULL);
    Preconditions.checkArgument(
        !Strings.isNullOrEmpty(options.getInstanceId()), INSTANCE_ID_EMPTY_OR_NULL);
    Preconditions.checkArgument(
        !Strings.isNullOrEmpty(options.getUserAgent()), USER_AGENT_EMPTY_OR_NULL);
    LOG.info(
        "Opening connection for projectId %s, instanceId %s, "
        + "on data host %s, table admin host %s.",
        options.getProjectId(), options.getInstanceId(), options.getDataHost(),
        options.getTableAdminHost());
    LOG.info("Bigtable options: %s.", options);

    Builder<ClientInterceptor> headerInterceptorBuilder = new ImmutableList.Builder<>();
    headerInterceptorBuilder.add(
        new GoogleCloudResourcePrefixInterceptor(options.getInstanceName().toString()));
    // Looking up Credentials takes time. Creating the retry executor and the EventLoopGroup don't
    // take as long, but still take time. Get the credentials on one thread, and start up the elg
    // and scheduledRetries thread pools on another thread.
    CredentialInterceptorCache credentialsCache = CredentialInterceptorCache.getInstance();
    RetryOptions retryOptions = options.getRetryOptions();
    CredentialOptions credentialOptions = options.getCredentialOptions();
    try {
      ClientInterceptor headerInterceptor =
          credentialsCache.getCredentialsInterceptor(credentialOptions, retryOptions);
      if (headerInterceptor != null) {
        headerInterceptorBuilder.add(headerInterceptor);
      }
    } catch (GeneralSecurityException e) {
      throw new IOException("Could not initialize credentials.", e);
    }

    headerInterceptors = headerInterceptorBuilder.build().toArray(new ClientInterceptor[0]);

    ChannelPool dataChannel = getDataChannelPool();

    BigtableSessionSharedThreadPools sharedPools = BigtableSessionSharedThreadPools.getInstance();

    // More often than not, users want the dataClient. Create a new one in the constructor.
    this.dataClient =
        new BigtableDataGrpcClient(dataChannel, sharedPools.getRetryExecutor(), options);
    dataClient.setCallOptionsFactory(
      new CallOptionsFactory.ConfiguredCallOptionsFactory(options.getCallOptionsConfig()));

    // Defer the creation of both the tableAdminClient until we need them.

    BigtableClientMetrics.counter(MetricLevel.Info, "sessions.active").inc();
    initializeResourceLimiter(options);
  }

  private ChannelPool getDataChannelPool() throws IOException {
    String host = options.getDataHost();
    int channelCount = options.getChannelCount();
    if (options.useCachedChannel()) {
      synchronized (BigtableSession.class) {
        // TODO: Ensure that the host and channelCount are the same.
        if (cachedDataChannelPool == null) {
          cachedDataChannelPool = createChannelPool(host, channelCount);
        }
        return cachedDataChannelPool;
      }
    }
    return createManagedPool(host, channelCount);
  }

  /**
   * Return options with legacy input options, if any, resolved into currently supported options.
   */
  private BigtableOptions resolveLegacyOptions(BigtableOptions options) throws IOException {
    if (options.getClusterId() != null && options.getZoneId() != null) {
      String instanceId =
          BigtableClusterUtilities.lookupInstanceId(
              options.getProjectId(),
              options.getClusterId(),
              options.getZoneId()
          );
      if (options.getInstanceId() != null) {
        Preconditions.checkArgument(
            options.getInstanceId().equals(instanceId),
            "Supplied instanceId: '%s', zoneId: '%s' and clusterId: '%s'. They do not match."
                + "\nFound instanceId '%s' that corresponds to the zoneId/clusterId",
            options.getInstanceId(),
            options.getZoneId(),
            options.getClusterId(),
            instanceId);
      }
      return options.toBuilder().setInstanceId(instanceId).build();
    }
    return options;
  }


  /**
   * Snapshot operations need various aspects of a {@link BigtableClusterName}. This method gets a
   * clusterId from either a lookup (projectId and instanceId translate to a single clusterId when
   * an instance has only one cluster) or from {@link BigtableOptions#getClusterId()}.
   */
  public synchronized BigtableClusterName getClusterName() throws IOException {
    if (this.clusterName == null) {
      try (BigtableClusterUtilities util =
          BigtableClusterUtilities.forInstance(options.getProjectId(), options.getInstanceId())) {
        ListClustersResponse clusters = util.getClusters();
        Preconditions.checkState(clusters.getClustersCount() == 1,
          String.format(
            "Project '%s' / Instance '%s' has %d clusters. There must be exactly 1 for this operation to work.",
            options.getProjectId(), options.getInstanceId(), clusters.getClustersCount()));
        clusterName = new BigtableClusterName(clusters.getClusters(0).getName());
      } catch (GeneralSecurityException e) {
        throw new IOException("Could not get cluster Id.", e);
      }
    }
    return clusterName;
  }

  /**
   * <p>Getter for the field <code>dataClient</code>.</p>
   *
   * @return a {@link com.google.cloud.bigtable.grpc.BigtableDataClient} object.
   */
  public BigtableDataClient getDataClient() {
    return dataClient;
  }

  /**
   * <p>createAsyncExecutor.</p>
   *
   * @return a {@link com.google.cloud.bigtable.grpc.async.AsyncExecutor} object.
   */
  public AsyncExecutor createAsyncExecutor() {
    return new AsyncExecutor(dataClient, resourceLimiter);
  }

  /**
   * <p>createBulkMutation.</p>
   *
   * @param tableName a {@link com.google.cloud.bigtable.grpc.BigtableTableName} object.
   * @param asyncExecutor a {@link com.google.cloud.bigtable.grpc.async.AsyncExecutor} object.
   * @return a {@link com.google.cloud.bigtable.grpc.async.BulkMutation} object.
   */
  public BulkMutation createBulkMutation(BigtableTableName tableName, AsyncExecutor asyncExecutor) {
    BulkOptions bulkOptions = options.getBulkOptions();
    if (bulkOptions.isEnableBulkMutationThrottling()) {
      resourceLimiter.throttle(bulkOptions.getBulkMutationRpcTargetMs());
    }
    return new BulkMutation(
        tableName,
        asyncExecutor,
        options.getRetryOptions(),
        BigtableSessionSharedThreadPools.getInstance().getRetryExecutor(),
        bulkOptions);
  }

  /**
   * <p>createBulkRead.</p>
   *
   * @param tableName a {@link com.google.cloud.bigtable.grpc.BigtableTableName} object.
   * @return a {@link com.google.cloud.bigtable.grpc.async.BulkRead} object.
   */
  public BulkRead createBulkRead(BigtableTableName tableName) {
    return new BulkRead(dataClient, tableName, options.getBulkOptions().getBulkMaxRowKeyCount(),
        BigtableSessionSharedThreadPools.getInstance().getBatchThreadPool()
    );
  }

  /**
   * <p>Getter for the field <code>tableAdminClient</code>.</p>
   *
   * @return a {@link com.google.cloud.bigtable.grpc.BigtableTableAdminClient} object.
   * @throws java.io.IOException if any.
   */
  public synchronized BigtableTableAdminClient getTableAdminClient() throws IOException {
    if (tableAdminClient == null) {
      ManagedChannel channel = createChannelPool(options.getTableAdminHost(), 1);
      tableAdminClient = new BigtableTableAdminGrpcClient(channel);
    }
    return tableAdminClient;
  }

  /**
   * <p>Getter for the field <code>instanceAdminClient</code>.</p>
   *
   * @return a {@link com.google.cloud.bigtable.grpc.BigtableInstanceClient} object.
   * @throws java.io.IOException if any.
   */
  public synchronized BigtableInstanceClient getInstanceAdminClient() throws IOException {
    if (instanceAdminClient == null) {
      ManagedChannel channel = createChannelPool(options.getInstanceAdminHost(), 1);
      instanceAdminClient = new BigtableInstanceGrpcClient(channel);
    }
    return instanceAdminClient;
  }

  /**
   * Create a new {@link com.google.cloud.bigtable.grpc.io.ChannelPool}, with auth headers.
   *
   * @param hostString a {@link java.lang.String} object.
   * @return a {@link com.google.cloud.bigtable.grpc.io.ChannelPool} object.
   * @throws java.io.IOException if any.
   */
  protected ChannelPool createChannelPool(final String hostString, int count) throws IOException {
    ChannelPool.ChannelFactory channelFactory = new ChannelPool.ChannelFactory() {
      @Override
      public ManagedChannel create() throws IOException {
        return createNettyChannel(hostString, options, headerInterceptors);
      }
    };
    return new ChannelPool(channelFactory, count);
  }

  /**
   * Create a new {@link com.google.cloud.bigtable.grpc.io.ChannelPool}, with auth headers, that
   * will be cleaned up when the connection closes.
   *
   * @param host a {@link java.lang.String} object.
   * @return a {@link com.google.cloud.bigtable.grpc.io.ChannelPool} object.
   * @throws java.io.IOException if any.
   */
  protected ChannelPool createManagedPool(String host, int channelCount) throws IOException {
    ChannelPool channelPool = createChannelPool(host, channelCount);
    managedChannels.add(channelPool);
    return channelPool;
  }

  /**
   * Create a new {@link com.google.cloud.bigtable.grpc.io.ChannelPool}, with auth headers.
   *
   * @param host a {@link String} object.
   * @param options a {@link BigtableOptions} object.
   * @return a {@link ChannelPool} object.
   * @throws IOException if any.
   * @throws GeneralSecurityException
   */
  public static ChannelPool createChannelPool(final String host, final BigtableOptions options)
      throws IOException, GeneralSecurityException {
    return createChannelPool(host, options, 1);
  }

  /**
   * Create a new {@link com.google.cloud.bigtable.grpc.io.ChannelPool}, with auth headers.
   *
   * @param host a {@link String} object specifying the host to connect to.
   * @param options a {@link BigtableOptions} object with the credentials, retry and other connection options.
   * @param count an int defining the number of channels to create
   * @return a {@link ChannelPool} object.
   * @throws IOException if any.
   * @throws GeneralSecurityException
   */
  public static ChannelPool createChannelPool(final String host, final BigtableOptions options, int count)
      throws IOException, GeneralSecurityException {
    final ClientInterceptor credentialsInterceptor = CredentialInterceptorCache.getInstance()
        .getCredentialsInterceptor(options.getCredentialOptions(), options.getRetryOptions());
    final ClientInterceptor prefixInterceptor =
        new GoogleCloudResourcePrefixInterceptor(options.getInstanceName().toString());
    return new ChannelPool(
        new ChannelPool.ChannelFactory() {
          @Override
          public ManagedChannel create() throws IOException {
            return createNettyChannel(host, options, credentialsInterceptor, prefixInterceptor);
          }
        }, count);
  }

  /**
   * <p>createNettyChannel.</p>
   *
   * @param host a {@link String} object.
   * @param options a {@link BigtableOptions} object.
   * @return a {@link ManagedChannel} object.
   * @throws SSLException if any.
   */
  public static ManagedChannel createNettyChannel(String host,
      BigtableOptions options, ClientInterceptor ... interceptors) throws SSLException {

    // Ideally, this should be ManagedChannelBuilder.forAddress(...) rather than an explicit
    // call to NettyChannelBuilder.  Unfortunately, that doesn't work for shaded artifacts.
    ManagedChannelBuilder<?> builder = NettyChannelBuilder
        .forAddress(host, options.getPort())
        .sslContext(createSslContext())
        ;

    if (options.usePlaintextNegotiation()) {
      builder.usePlaintext(true);
    }
    return builder
        .nameResolverFactory(new DnsNameResolverProvider())
        .idleTimeout(Long.MAX_VALUE, TimeUnit.SECONDS)
        .maxInboundMessageSize(MAX_MESSAGE_SIZE)
        .userAgent(BigtableVersionInfo.CORE_UESR_AGENT + "," + options.getUserAgent())
        .intercept(interceptors)
        .build();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void close() throws IOException {
    if (managedChannels.isEmpty()) {
      return;
    }
    long timeoutNanos = TimeUnit.SECONDS.toNanos(10);
    long endTimeNanos = System.nanoTime() + timeoutNanos;
    for (ManagedChannel channel : managedChannels) {
      channel.shutdown();
    }
    for (ManagedChannel channel : managedChannels) {
      long awaitTimeNanos = endTimeNanos - System.nanoTime();
      if (awaitTimeNanos <= 0) {
        break;
      }
      try {
        channel.awaitTermination(awaitTimeNanos, TimeUnit.NANOSECONDS);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IOException("Interrupted while closing the channelPools");
      }
    }
    for (ManagedChannel channel : managedChannels) {
      if (!channel.isTerminated()) {
        // Sometimes, gRPC channels don't close properly. We cannot explain why that happens,
        // nor can we reproduce the problem reliably. However, that doesn't actually cause
        // problems. Synchronous RPCs will throw exceptions right away. Buffered Mutator based
        // async operations are already logged. Direct async operations may have some trouble,
        // but users should not currently be using them directly.
        //
        // NOTE: We haven't seen this problem since removing the RefreshingChannel
        LOG.info("Could not close the channel after 10 seconds.");
        break;
      }
    }
    managedChannels.clear();
    BigtableClientMetrics.counter(MetricLevel.Info, "sessions.active").dec();
  }

  /**
   * <p>Getter for the field <code>options</code>.</p>
   *
   * @return a {@link com.google.cloud.bigtable.config.BigtableOptions} object.
   */
  public BigtableOptions getOptions() {
    return this.options;
  }
}
