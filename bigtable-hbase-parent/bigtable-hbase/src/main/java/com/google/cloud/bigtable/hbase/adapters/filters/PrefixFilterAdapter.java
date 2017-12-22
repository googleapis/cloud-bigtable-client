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
package com.google.cloud.bigtable.hbase.adapters.filters;

import com.google.bigtable.v2.RowFilter;
import com.google.cloud.bigtable.filter.RowFilters;
import com.google.cloud.bigtable.hbase.adapters.read.ReaderExpressionHelper;
import com.google.cloud.bigtable.util.ByteStringer;
import com.google.cloud.bigtable.util.RowKeyUtil;
import com.google.cloud.bigtable.util.RowKeyWrapper;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.protobuf.ByteString;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.hadoop.hbase.filter.PrefixFilter;

/**
 * Adapter for HBase {@link org.apache.hadoop.hbase.filter.PrefixFilter} instances.
 *
 * @author sduskis
 * @version $Id: $Id
 */
public class PrefixFilterAdapter extends TypedFilterAdapterBase<PrefixFilter> {

  /**
   * {@inheritDoc}
   */
  @Override
  public RowFilter adapt(FilterAdapterContext context, PrefixFilter filter)
      throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream(filter.getPrefix().length * 2);
    ReaderExpressionHelper.writeQuotedRegularExpression(baos, filter.getPrefix());
    // Unquoted all bytes:
    baos.write(ReaderExpressionHelper.ALL_QUALIFIERS_BYTES);
    ByteString quotedValue = ByteStringer.wrap(baos.toByteArray());
    return RowFilters.RF.rowKeyRegex(quotedValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FilterSupportStatus isFilterSupported(
      FilterAdapterContext context, PrefixFilter filter) {
    return FilterSupportStatus.SUPPORTED;
  }

  @Override
  public RangeSet<RowKeyWrapper> getIndexScanHint(PrefixFilter filter) {
    if (filter.getPrefix().length == 0) {
      return ImmutableRangeSet.of(Range.<RowKeyWrapper>all());
    } else {
      ByteString start = ByteString.copyFrom(filter.getPrefix());
      ByteString end = ByteString.copyFrom(
          RowKeyUtil.calculateTheClosestNextRowKeyForPrefix(filter.getPrefix())
      );
      return ImmutableRangeSet.of(
          Range.closedOpen(new RowKeyWrapper(start), new RowKeyWrapper(end))
      );
    }
  }
}
