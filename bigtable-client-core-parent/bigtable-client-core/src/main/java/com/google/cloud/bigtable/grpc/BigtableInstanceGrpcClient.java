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

import com.google.api.client.util.BackOff;
import com.google.api.client.util.ExponentialBackOff;
import com.google.bigtable.admin.v2.BigtableInstanceAdminGrpc;
import com.google.bigtable.admin.v2.Cluster;
import com.google.bigtable.admin.v2.CreateInstanceRequest;
import com.google.bigtable.admin.v2.DeleteInstanceRequest;
import com.google.bigtable.admin.v2.GetClusterRequest;
import com.google.bigtable.admin.v2.Instance;
import com.google.bigtable.admin.v2.ListClustersRequest;
import com.google.bigtable.admin.v2.ListClustersResponse;
import com.google.bigtable.admin.v2.ListInstancesRequest;
import com.google.bigtable.admin.v2.ListInstancesResponse;
import com.google.common.primitives.Ints;
import com.google.longrunning.GetOperationRequest;
import com.google.longrunning.Operation;
import com.google.longrunning.OperationsGrpc;
import com.google.protobuf.Empty;

import io.grpc.Channel;
import io.grpc.protobuf.StatusProto;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <p>BigtableInstanceGrpcClient class.</p>
 *
 * @author sduskis
 * @version $Id: $Id
 */
public class BigtableInstanceGrpcClient implements BigtableInstanceClient {

  private final BigtableInstanceAdminGrpc.BigtableInstanceAdminBlockingStub instanceClient;
  private final OperationsGrpc.OperationsBlockingStub operationsStub;

  /**
   * <p>Constructor for BigtableInstanceGrpcClient.</p>
   *
   * @param channel a {@link io.grpc.Channel} object.
   */
  public BigtableInstanceGrpcClient(Channel channel) {
    this.instanceClient = BigtableInstanceAdminGrpc.newBlockingStub(channel);
    operationsStub = OperationsGrpc.newBlockingStub(channel);
  }

  /** {@inheritDoc} */
  @Override
  public Operation createInstance(CreateInstanceRequest request) {
    return instanceClient.createInstance(request);
  }

  /** {@inheritDoc} */
  @Override
  public Operation getOperation(GetOperationRequest request) {
    return operationsStub.getOperation(request);
  }

  /** {@inheritDoc} */
  @Override
  public void waitForOperation(Operation operation) throws InterruptedException, TimeoutException {
    waitForOperation(operation, 10, TimeUnit.MINUTES);
  }

  /** {@inheritDoc} */
  public void waitForOperation(Operation operation, long timeout, TimeUnit timeUnit)
      throws TimeoutException, InterruptedException {
    GetOperationRequest request = GetOperationRequest.newBuilder()
        .setName(operation.getName())
        .build();

    ExponentialBackOff backOff = new ExponentialBackOff.Builder()
        .setInitialIntervalMillis(100)
        .setMultiplier(1.3)
        .setMaxIntervalMillis(Ints.checkedCast(TimeUnit.SECONDS.toMillis(60)))
        .setMaxElapsedTimeMillis(Ints.checkedCast(timeUnit.toMillis(timeout)))
        .build();

    Operation currentOperationState = operation;

    while (true) {
      if (currentOperationState.getDone()) {
        switch (currentOperationState.getResultCase()) {
          case RESPONSE:
            return;
          case ERROR:
            throw StatusProto.toStatusRuntimeException(currentOperationState.getError());
          case RESULT_NOT_SET:
            throw new IllegalStateException(
                "System returned invalid response for Operation check: " + currentOperationState);
        }
      }

      final long backOffMillis;
      try {
        backOffMillis = backOff.nextBackOffMillis();
      } catch (IOException e) {
        //Will never happen
        throw new RuntimeException(e);
      }
      if (backOffMillis == BackOff.STOP) {
        throw new TimeoutException("Operation did not complete in time");
      } else {
        Thread.sleep(backOffMillis);
      }

      currentOperationState = getOperation(request);
    }
  }

  /** {@inheritDoc} */
  @Override
  public ListInstancesResponse listInstances(ListInstancesRequest request) {
    return instanceClient.listInstances(request);
  }

  /** {@inheritDoc} */
  @Override
  public Instance updateInstance(Instance instance) {
    return instanceClient.updateInstance(instance);
  }

  /** {@inheritDoc} */
  @Override
  public Empty deleteInstance(DeleteInstanceRequest request) {
    return instanceClient.deleteInstance(request);
  }

  /** {@inheritDoc} */
  @Override
  public Cluster getCluster(GetClusterRequest request) {
    return instanceClient.getCluster(request);
  }

  /** {@inheritDoc} */
  @Override
  public ListClustersResponse listCluster(ListClustersRequest request) {
    return instanceClient.listClusters(request);
  }

  /** {@inheritDoc} */
  @Override
  public Operation updateCluster(Cluster cluster) {
    return instanceClient.updateCluster(cluster);
  }
}
