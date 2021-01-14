/*
 * Copyright 2021 Google LLC
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
package com.google.cloud.bigtable.beam.hbasesnapshots;

import com.google.bigtable.repackaged.com.google.api.core.InternalExtensionOnly;
import com.google.cloud.bigtable.beam.CloudBigtableIO;
import com.google.cloud.bigtable.beam.CloudBigtableTableConfiguration;
import com.google.cloud.bigtable.beam.TemplateUtils;
import com.google.cloud.bigtable.beam.sequencefiles.HBaseResultToMutationFn;
import com.google.cloud.bigtable.beam.sequencefiles.ImportJob;
import com.google.cloud.bigtable.beam.sequencefiles.Utils;
import com.google.common.annotations.VisibleForTesting;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.hadoop.format.HadoopFormatIO;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;

/**
 * A job that imports data from HBase snapshot exports hosted in Cloud Storage bucket into Cloud
 * Bigtable. This job can be run directly or as a Dataflow template.
 *
 * <p>Execute the following command to run the job directly:
 *
 * <pre>
 * mvn compile exec:java \
 *   -DmainClass=com.google.cloud.bigtable.beam.hbasesnapshots.ImportJobFromHbaseSnapshot \
 *   -Dexec.args="--runner=DataflowRunner \
 *                --stagingLocation=gs://$STAGING_PATH \
 *                --project=$PROJECT \
 *                --bigtableInstanceId=$INSTANCE \
 *                --bigtableTableId=$TABLE \
 *                --gcsProject=$PROJECT \
 *                --hbaseRootDir=gs://$HBASE_EXPORT_ROOT_PATH \
 *                --snapshotName=$SNAPSHOT_NAME  \
 *                --restoreDir=gs://$RESTORE_PATH
 * </pre>
 */
@InternalExtensionOnly
public class ImportJobFromHbaseSnapshot {
  private static final Log LOG = LogFactory.getLog(ImportJobFromHbaseSnapshot.class);

  public interface ImportOptions extends ImportJob.ImportOptions {
    @Description("The GCP project id for the GCS bucket")
    String getGcsProject();

    @SuppressWarnings("unused")
    void setGcsProject(String gcsProjectId);

    @Description("The HBase root dir where HBase snapshot files resides.")
    String getHbaseRootDir();

    @SuppressWarnings("unused")
    void setHbaseRootDir(String hbaseRootDir);

    @Description("Temporal location for restoring snapshots")
    String getRestoreDir();

    @SuppressWarnings("unused")
    void setRestoreDir(String restoreDir);

    @Description("Snapshot name")
    String getSnapshotName();

    @SuppressWarnings("unused")
    void setSnapshotName(String snapshotName);
  }

  public static void main(String[] args) {
    PipelineOptionsFactory.register(ImportOptions.class);

    ImportOptions opts =
        PipelineOptionsFactory.fromArgs(args).withValidation().as(ImportOptions.class);

    LOG.info("DEBUG===> Building Pipeline");
    Pipeline pipeline = buildPipeline(opts);

    LOG.info("DEBUG===> Running Pipeline");
    PipelineResult result = pipeline.run();

    if (opts.getWait()) {
      Utils.waitForPipelineToFinish(result);
    }
  }

  @VisibleForTesting
  static Pipeline buildPipeline(ImportOptions opts) {
    Pipeline pipeline = Pipeline.create(Utils.tweakOptions(opts));
    pipeline
        .apply(
            "Read from HBase Snapshot",
            HadoopFormatIO.<ImmutableBytesWritable, Result>read()
                .withConfiguration(
                    new HBaseSnapshotInputConfigBuilder()
                        .setProjectId(opts.getGcsProject())
                        .setExportedSnapshotDir(opts.getHbaseRootDir())
                        .setSnapshotName(opts.getSnapshotName())
                        .setRestoreDir(opts.getRestoreDir())
                        .build()))
        .apply("Create Mutations", ParDo.of(new HBaseResultToMutationFn()))
        .apply("Write to Bigtable", createSink(opts));

    return pipeline;
  }

  static PTransform<PCollection<Mutation>, PDone> createSink(ImportOptions opts) {
    CloudBigtableTableConfiguration config = TemplateUtils.BuildImportConfig(opts);
    return CloudBigtableIO.writeToTable(config);
  }
}
