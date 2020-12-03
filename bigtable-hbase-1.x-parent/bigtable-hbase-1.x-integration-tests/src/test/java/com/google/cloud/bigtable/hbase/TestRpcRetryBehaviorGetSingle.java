/*
 * Copyright 2020 Google LLC
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
package com.google.cloud.bigtable.hbase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import com.google.bigtable.v2.BigtableGrpc;
import com.google.bigtable.v2.ReadRowsRequest;
import com.google.bigtable.v2.ReadRowsResponse;
import com.google.cloud.bigtable.grpc.scanner.BigtableRetriesExhaustedException;
import com.google.common.collect.ImmutableMap;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Table;
import org.hamcrest.CoreMatchers;

public class TestRpcRetryBehaviorGetSingle extends TestRpcRetryBehavior {
  private final AtomicInteger numReadRowsInvocations = new AtomicInteger();

  @Override
  protected ImmutableMap.Builder<String, String> defineProperties() {
    ImmutableMap.Builder<String, String> connPropsBuilder =
        ImmutableMap.<String, String>builder()
            .put(BigtableOptionsFactory.BIGTABLE_USE_TIMEOUTS_KEY, String.valueOf(timeoutEnabled))
            .put(
                BigtableOptionsFactory.BIGTABLE_RPC_TIMEOUT_MS_KEY,
                String.valueOf(operationTimeoutMs));

    if (attemptTimeoutEnabled) {
      connPropsBuilder.put(
          BigtableOptionsFactory.BIGTABLE_RPC_ATTEMPT_TIMEOUT_MS_KEY,
          String.valueOf(attemptTimeoutMs));

      // Set this to a low number to validate that it is not used when timeouts are enabled.
      connPropsBuilder.put(BigtableOptionsFactory.MAX_ELAPSED_BACKOFF_MILLIS_KEY, "123");
    } else {
      connPropsBuilder.put(
          BigtableOptionsFactory.MAX_ELAPSED_BACKOFF_MILLIS_KEY,
          String.valueOf(maxElapsedBackoffMs));
    }
    return connPropsBuilder;
  }

  @Override
  protected void executeLogic(Table table) throws Exception {
    try {
      table.get(new Get("mykey".getBytes()));

      fail("Should have errored out");
    } catch (Exception e) {
      assertThat(e.getCause(), CoreMatchers.instanceOf(BigtableRetriesExhaustedException.class));
    }
  }

  @Override
  protected AtomicInteger getInvocations() {
    return numReadRowsInvocations;
  }

  @Override
  protected BigtableGrpc.BigtableImplBase setupRpcCall() {
    return new BigtableGrpc.BigtableImplBase() {
      @Override
      public void readRows(
          ReadRowsRequest request, StreamObserver<ReadRowsResponse> responseObserver) {
        numReadRowsInvocations.incrementAndGet();
      }
    };
  }
}
