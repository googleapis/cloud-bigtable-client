/*
 * Copyright 2020 Google Inc. All Rights Reserved.
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
package com.google.cloud.bigtable.beam.validation;

import static com.google.cloud.bigtable.beam.validation.SyncTableUtils.immutableBytesToString;

import com.google.api.core.InternalApi;
import com.google.cloud.bigtable.beam.validation.HadoopHashTableSource.RangeHash;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.ListCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.BoundedSource;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.values.KV;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Buffers the RangeHashes generated by {@link HadoopHashTableSource}. This is an optimization that
 * allows {@link ComputeAndValidateHashFromBigtableDoFn} to issue fewer ReadRow APIs with larger row
 * ranges.
 *
 * <p>Hadoop HashTable output is sorted by row-key and contains a row-range and hash. Beam
 * Pcollection do not guarantee any ordering. To fetch a batch of ranges in 1 ReadRows operation,
 * this source buffers then and outputs a List<RangeHash> guaranteeing the sorted order of ranges.
 */
@InternalApi
class BufferedHadoopHashTableSource extends BoundedSource<KV<String, List<RangeHash>>> {

  private static final long serialVersionUID = 39842743L;

  public static final Log LOG = LogFactory.getLog(BufferedHadoopHashTableSource.class);
  private static final int DEFAULT_BATCH_SIZE = 50;

  // Max number of RangeHashes to buffer.
  private int maxBufferSize;
  private HadoopHashTableSource hashTableSource;
  private Coder<KV<String, List<RangeHash>>> coder;

  public BufferedHadoopHashTableSource(HadoopHashTableSource source) {
    this(source, DEFAULT_BATCH_SIZE);
  }

  public BufferedHadoopHashTableSource(HadoopHashTableSource hashTableSource, int maxBufferSize) {
    this.hashTableSource = hashTableSource;
    this.coder = KvCoder.of(StringUtf8Coder.of(), ListCoder.of(RangeHashCoder.of()));
    this.maxBufferSize = maxBufferSize;
  }

  @Override
  public List<? extends BoundedSource<KV<String, List<RangeHash>>>> split(
      long desiredBundleSizeBytes, PipelineOptions options) throws IOException {

    List<HadoopHashTableSource> splitHashTableSources =
        (List<HadoopHashTableSource>) hashTableSource.split(desiredBundleSizeBytes, options);

    List<BufferedHadoopHashTableSource> splitSources =
        new ArrayList<>(splitHashTableSources.size());
    // Keep the splits same as HashTableSource.
    for (HadoopHashTableSource splitHashTableSource : splitHashTableSources) {
      // Add the last range for [lastPartition, stopRow).
      splitSources.add(new BufferedHadoopHashTableSource(splitHashTableSource));
    }
    return splitSources;
  }

  @Override
  public Coder<KV<String, List<RangeHash>>> getOutputCoder() {
    return coder;
  }

  @Override
  public long getEstimatedSizeBytes(PipelineOptions options) throws Exception {
    // HashTable data files don't expose a method to estimate size or lineCount.
    return 0;
  }

  @Override
  public BoundedReader createReader(PipelineOptions options) throws IOException {
    return new BufferedHashBasedReader(this, hashTableSource.createReader(options));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BufferedHadoopHashTableSource)) {
      return false;
    }
    BufferedHadoopHashTableSource that = (BufferedHadoopHashTableSource) o;
    return maxBufferSize == that.maxBufferSize
        && Objects.equal(hashTableSource, that.hashTableSource);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(maxBufferSize, hashTableSource);
  }

  @Override
  public String toString() {
    return "BufferedHadoopHashTableSource ["
        + immutableBytesToString(hashTableSource.startRowInclusive)
        + ", "
        + immutableBytesToString(hashTableSource.stopRowExclusive)
        + "), maxBufferSize="
        + maxBufferSize;
  }

  private void writeObject(ObjectOutputStream s) throws IOException {
    s.writeObject(hashTableSource);
    s.writeInt(maxBufferSize);
  }

  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    this.hashTableSource = (HadoopHashTableSource) s.readObject();
    this.coder = KvCoder.of(StringUtf8Coder.of(), ListCoder.of(RangeHashCoder.of()));
    this.maxBufferSize = s.readInt();
  }

  private static class BufferedHashBasedReader extends BoundedReader<KV<String, List<RangeHash>>> {

    private BoundedReader<RangeHash> hashReader;
    private BufferedHadoopHashTableSource source;

    private List<RangeHash> buffer;

    public BufferedHashBasedReader(
        BufferedHadoopHashTableSource source, BoundedReader<RangeHash> hashReader) {
      this.source = source;
      this.hashReader = hashReader;
      this.buffer = new ArrayList<>(source.maxBufferSize);
    }

    @Override
    public boolean start() throws IOException {
      if (!hashReader.start()) {
        // HashReader does not have any hashes, return empty reader.
        return false;
      }
      // Start returned true, consume the current RangeHash.
      buffer.add(hashReader.getCurrent());
      bufferRangeHashes();
      // Buffer is not empty, return true to consume the current buffer.
      return true;
    }

    // Reads from hashReader and buffers the RangeHashes.
    // Returns true if any RangeHashes were read from hashReader.
    private boolean bufferRangeHashes() throws IOException {
      boolean readRangeHashes = false;
      while (buffer.size() < source.maxBufferSize && hashReader.advance()) {
        readRangeHashes = true;
        buffer.add(hashReader.getCurrent());
      }
      return readRangeHashes;
    }

    @Override
    public boolean advance() throws IOException {
      return bufferRangeHashes();
    }

    @Override
    public KV<String, List<RangeHash>> getCurrent() {
      // getCurrent only gets called when buffer is not empty.
      Preconditions.checkArgument(!buffer.isEmpty(), "Can not get current on empty buffer.");
      List<RangeHash> hashes = buffer;
      // Reset the buffer for next batch.
      buffer = new ArrayList<>(source.maxBufferSize);
      // GroupBy key is a string and not ImmutableBytesWritable because the WritableCoder is not
      // deterministic. The outputted PCollection is grouped by the K and needs a deterministic
      // coder. Having a String K leads to an unfortunate double encoding, ImmutableBytesWritable->
      // HEX string -> UTF8 encoded string. The number of batches are significantly smaller than
      // data fetched from Bigtable and should not have meaningful impact on the job performance.
      return KV.of(Bytes.toStringBinary(hashes.get(0).startInclusive.copyBytes()), hashes);
    }

    @Override
    public void close() throws IOException {
      hashReader.close();
    }

    @Override
    public BoundedSource<KV<String, List<RangeHash>>> getCurrentSource() {
      return source;
    }
  }
}
