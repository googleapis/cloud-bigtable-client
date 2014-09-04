/*
 * Copyright (c) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.cloud.anviltop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public abstract class AbstractTest {
  protected DataGenerationHelper dataHelper = new DataGenerationHelper();

  @Rule
  public TestRule loggingRule = new TestRule() {
    @Override
    public Statement apply(Statement base, Description description) {
      System.out.println(String.format("Running: %s",description.getDisplayName()));

      return base;
    }
  };

  // Populated by our connectionResource rule below:
  public HConnection connection;
  // A new connection is generated per test:
  @Rule
  public ExternalResource connectionResource = new ExternalResource() {
    @Override
    public Statement apply(Statement base, Description description) {
      return super.apply(base, description);
    }

    @Override
    protected void before() throws Throwable {
      connection = createNewConnection();
    }

    @Override
    protected void after() {
      try {
        connection.close();
      } catch (IOException e) {
        throw new RuntimeException("Failed to close connection after test completion.");
      }
    }
  };

  // This is for when we need to look at the results outside of the current connection
  public HConnection createNewConnection() throws IOException {
    Configuration conf = IntegrationTests.getConfiguration();
    return HConnectionManager.createConnection(conf);
  }

  protected static class QualifierValue implements Comparable<QualifierValue> {
    protected final byte[] qualifier;
    protected final byte[] value;

    public QualifierValue(@NotNull byte[] qualifier, @NotNull byte[] value) {
      this.qualifier = qualifier;
      this.value = value;
    }

    @Override
    public int compareTo(QualifierValue qualifierValue) {
      return Bytes.compareTo(this.qualifier, qualifierValue.qualifier);
    }
  }
}
