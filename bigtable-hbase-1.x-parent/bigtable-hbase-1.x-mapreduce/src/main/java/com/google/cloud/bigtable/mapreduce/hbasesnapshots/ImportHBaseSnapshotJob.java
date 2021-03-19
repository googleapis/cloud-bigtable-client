/*
 * Copyright 2021 Google Inc. All Rights Reserved.
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
package com.google.cloud.bigtable.mapreduce.hbasesnapshots;

import static com.google.cloud.bigtable.mapreduce.hbasesnapshots.ImportJobCommon.BIGTABLE_MUTATE_RPC_TIMEOUT_MS_DEFAULT;
import static com.google.cloud.bigtable.mapreduce.hbasesnapshots.ImportJobCommon.IMPORT_SNAPSHOT_JOBNAME_KEY;
import static com.google.cloud.bigtable.mapreduce.hbasesnapshots.ImportJobCommon.SNAPSHOTNAME_KEY;
import static com.google.cloud.bigtable.mapreduce.hbasesnapshots.ImportJobCommon.SNAPSHOT_RESTOREDIR_KEY;
import static com.google.cloud.bigtable.mapreduce.hbasesnapshots.ImportJobCommon.TABLENAME_KEY;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import com.google.cloud.bigtable.hbase.BigtableOptionsFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.MutationSerialization;
import org.apache.hadoop.hbase.mapreduce.ResultSerialization;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A Mapreduce job to import an HBase snapshot to Bigtable. */
public class ImportHBaseSnapshotJob extends Configured implements Tool {

  private static final Log LOG = LogFactory.getLog(ImportHBaseSnapshotJob.class);

  /*
   * @param errorMsg Error message.  Can be null.
   */
  private static void usage(final String errorMsg) {
    if (errorMsg != null && errorMsg.length() > 0) {
      System.out.println("ERROR: " + errorMsg);
    }
    System.out.println(
        "Usage: hadoop jar <JAR> <CLASS> <properties> <snapshot-name> <snapshot-dir> <tablename> <temp-dir>\n"
            + "Required properties: \n"
            + "  -D"
            + BigtableOptionsFactory.PROJECT_ID_KEY
            + "=<bigtable-project-id>\n"
            + "  -D"
            + BigtableOptionsFactory.INSTANCE_ID_KEY
            + "=<bigtable-instance-id>\n"
            + "Required arguments:\n"
            + "  snapshot-name     - name of hbase snapshot to read\n"
            + "  snapshot-dir      - directory of snapshot, e.g., gs://hbase-migration-table1-bucket/export/table1-snapshot\n"
            + "  tablename         - bigtable table to import into\n"
            + "  temp-dir          - a temporary base directory to restore hlinks of the snapshot into for read, e.g., gs://hbase-migration-table1-bucket/export/table1-restore\n");

    System.exit(1);
  }

  /**
   * Job runner
   *
   * @param args command line input
   * @return job success flag
   * @throws Exception terminal for application failure
   */
  @Override
  public int run(String[] args) throws Exception {
    Configuration conf = HBaseConfiguration.create(getConf());
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

    if (setConfFromArgs(conf, otherArgs) != 0) return -1;

    LOG.info(
        "Using Arguments: "
            + "\n"
            + "snapshotName = "
            + conf.get(SNAPSHOTNAME_KEY)
            + "\n"
            + "snapshotDir = "
            + conf.get(HConstants.HBASE_DIR)
            + "\n"
            + "bigtableName = "
            + conf.get(TableOutputFormat.OUTPUT_TABLE)
            + "\n"
            + "tempDir = "
            + conf.get(SNAPSHOT_RESTOREDIR_KEY)
            + "\n"
            + CommonConfigurationKeysPublic.FS_DEFAULT_NAME_KEY
            + " = "
            + conf.get(CommonConfigurationKeysPublic.FS_DEFAULT_NAME_KEY)
            + "\n");

    Job job = createSubmittableJob(conf);
    int successStatus = (job.waitForCompletion(true) ? 0 : 1);

    Counters counters = job.getCounters();
    long numRows = counters.findCounter(ScanCounter.NUM_ROWS).getValue();
    long numCells = counters.findCounter(ScanCounter.NUM_CELLS).getValue();

    System.out.println(("############# Num of ROWS imported= " + numRows));
    System.out.println(("############# Num of CELLS imported= " + numCells));

    return successStatus;
  }

  protected static int setConfFromArgs(Configuration conf, String[] args) {
    // basic pre-condition check
    if (args.length < 4) {
      usage("Wrong number of arguments: " + args.length);
      return -1;
    }
    if (conf.get(BigtableOptionsFactory.PROJECT_ID_KEY) == null) {
      usage("Must specify the property " + BigtableOptionsFactory.PROJECT_ID_KEY);
      return -1;
    }
    if (conf.get(BigtableOptionsFactory.INSTANCE_ID_KEY) == null) {
      usage("Must specify the property " + BigtableOptionsFactory.INSTANCE_ID_KEY);
      return -1;
    }

    // set arguments in configuration for ease of use
    conf.set(SNAPSHOTNAME_KEY, args[0]);
    conf.set(HConstants.HBASE_DIR, args[1]);
    conf.set(TABLENAME_KEY, args[2]);
    conf.set(SNAPSHOT_RESTOREDIR_KEY, args[3]);

    // set default fs
    conf.set(CommonConfigurationKeysPublic.FS_DEFAULT_NAME_KEY, conf.get(HConstants.HBASE_DIR));

    // default job name
    conf.setIfUnset(
        IMPORT_SNAPSHOT_JOBNAME_KEY,
        ImportHBaseSnapshotJob.class.getName()
            + " - from snapshot "
            + conf.get(SNAPSHOTNAME_KEY)
            + " to "
            + conf.get(TABLENAME_KEY));

    // implicit bigtable configs
    BigtableConfiguration.configure(
        conf,
        conf.get(BigtableOptionsFactory.PROJECT_ID_KEY),
        conf.get(BigtableOptionsFactory.INSTANCE_ID_KEY),
        conf.get(BigtableOptionsFactory.APP_PROFILE_ID_KEY, ""));
    conf.setBooleanIfUnset(BigtableOptionsFactory.BIGTABLE_USE_TIMEOUTS_KEY, true);
    conf.setIfUnset(
        BigtableOptionsFactory.BIGTABLE_MUTATE_RPC_TIMEOUT_MS_KEY,
        String.valueOf(BIGTABLE_MUTATE_RPC_TIMEOUT_MS_DEFAULT));

    // implicit table outputformat configs that are used in the job to write map output to a table
    conf.set(TableOutputFormat.OUTPUT_TABLE, conf.get(TABLENAME_KEY));
    conf.setStrings(
        "io.serializations",
        conf.get("io.serializations"),
        MutationSerialization.class.getName(),
        ResultSerialization.class.getName());

    return 0;
  }

  /**
   * Sets up the actual job.
   *
   * @param conf The current configuration.
   * @return The newly created job.
   * @throws java.io.IOException When setting up the job fails.
   */
  protected static Job createSubmittableJob(Configuration conf) throws IOException {
    Job job = Job.getInstance(conf, conf.get(IMPORT_SNAPSHOT_JOBNAME_KEY));
    job.setJarByClass(ImportHBaseSnapshotJob.class);
    TableMapReduceUtil.initTableSnapshotMapperJob(
        conf.get(SNAPSHOTNAME_KEY),
        new Scan().setMaxVersions(),
        SnapshotMapper.class,
        ImmutableBytesWritable.class,
        Writable.class,
        job,
        true,
        new Path(conf.get(SNAPSHOT_RESTOREDIR_KEY)));

    job.setNumReduceTasks(0);
    job.setOutputFormatClass(TableOutputFormat.class);

    return job;
  }

  protected enum ScanCounter {
    NUM_ROWS,
    NUM_CELLS,
  }

  /** MAPPER */
  protected static class SnapshotMapper extends TableMapper<ImmutableBytesWritable, Put> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnapshotMapper.class);

    private Boolean isEmptyRowWarned = false;

    protected static final int MAX_CELLS = 100_000 - 1;

    @Override
    protected void setup(Context ctx) {
      // noop
    }

    @Override
    protected void map(ImmutableBytesWritable key, Result result, Context ctx)
        throws IOException, InterruptedException {

      List<Cell> cells = checkEmptyRow(result);
      if (cells.isEmpty()) {
        return;
      }

      ctx.getCounter(ScanCounter.NUM_ROWS).increment(1);
      ctx.getCounter(ScanCounter.NUM_CELLS).increment(cells.size());

      // Split the row into multiple puts if it exceeds the maximum mutation limit
      Iterator<Cell> cellIt = cells.iterator();

      while (cellIt.hasNext()) {
        Put put = new Put(key.get());

        for (int i = 0; i < MAX_CELLS && cellIt.hasNext(); i++) {
          put.add(cellIt.next());
        }

        ctx.write(key, put);
      }
    }

    @Override
    protected void cleanup(Context ctx) {
      // noop
    }

    // Warns about empty row on first occurrence only and replaces a null array with 0-length one.
    private List<Cell> checkEmptyRow(Result result) {
      List<Cell> cells = result.listCells();
      if (cells == null) {
        cells = Collections.emptyList();
      }
      if (!isEmptyRowWarned && cells.isEmpty()) {
        LOGGER.warn("Encountered empty row. Was input file serialized by HBase 0.94?");
        isEmptyRowWarned = true;
      }
      return cells;
    }
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new ImportHBaseSnapshotJob(), args);

    if (exitCode == 0) {
      System.out.println("job appears to have completed successfully.");
    } else {
      System.err.println("job is exiting with exit code='" + exitCode + "'");
    }

    System.exit(exitCode);
  }
}