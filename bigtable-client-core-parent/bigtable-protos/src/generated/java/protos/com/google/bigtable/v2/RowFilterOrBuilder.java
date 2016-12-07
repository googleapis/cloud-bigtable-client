// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/v2/data.proto

package com.google.bigtable.v2;

public interface RowFilterOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.bigtable.v2.RowFilter)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Applies several RowFilters to the data in sequence, progressively
   * narrowing the results.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter.Chain chain = 1;</code>
   */
  com.google.bigtable.v2.RowFilter.Chain getChain();
  /**
   * <pre>
   * Applies several RowFilters to the data in sequence, progressively
   * narrowing the results.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter.Chain chain = 1;</code>
   */
  com.google.bigtable.v2.RowFilter.ChainOrBuilder getChainOrBuilder();

  /**
   * <pre>
   * Applies several RowFilters to the data in parallel and combines the
   * results.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter.Interleave interleave = 2;</code>
   */
  com.google.bigtable.v2.RowFilter.Interleave getInterleave();
  /**
   * <pre>
   * Applies several RowFilters to the data in parallel and combines the
   * results.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter.Interleave interleave = 2;</code>
   */
  com.google.bigtable.v2.RowFilter.InterleaveOrBuilder getInterleaveOrBuilder();

  /**
   * <pre>
   * Applies one of two possible RowFilters to the data based on the output of
   * a predicate RowFilter.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter.Condition condition = 3;</code>
   */
  com.google.bigtable.v2.RowFilter.Condition getCondition();
  /**
   * <pre>
   * Applies one of two possible RowFilters to the data based on the output of
   * a predicate RowFilter.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter.Condition condition = 3;</code>
   */
  com.google.bigtable.v2.RowFilter.ConditionOrBuilder getConditionOrBuilder();

  /**
   * <pre>
   * ADVANCED USE ONLY.
   * Hook for introspection into the RowFilter. Outputs all cells directly to
   * the output of the read rather than to any parent filter. Consider the
   * following example:
   *     Chain(
   *       FamilyRegex("A"),
   *       Interleave(
   *         All(),
   *         Chain(Label("foo"), Sink())
   *       ),
   *       QualifierRegex("B")
   *     )
   *                         A,A,1,w
   *                         A,B,2,x
   *                         B,B,4,z
   *                            |
   *                     FamilyRegex("A")
   *                            |
   *                         A,A,1,w
   *                         A,B,2,x
   *                            |
   *               +------------+-------------+
   *               |                          |
   *             All()                    Label(foo)
   *               |                          |
   *            A,A,1,w              A,A,1,w,labels:[foo]
   *            A,B,2,x              A,B,2,x,labels:[foo]
   *               |                          |
   *               |                        Sink() --------------+
   *               |                          |                  |
   *               +------------+      x------+          A,A,1,w,labels:[foo]
   *                            |                        A,B,2,x,labels:[foo]
   *                         A,A,1,w                             |
   *                         A,B,2,x                             |
   *                            |                                |
   *                    QualifierRegex("B")                      |
   *                            |                                |
   *                         A,B,2,x                             |
   *                            |                                |
   *                            +--------------------------------+
   *                            |
   *                         A,A,1,w,labels:[foo]
   *                         A,B,2,x,labels:[foo]  // could be switched
   *                         A,B,2,x               // could be switched
   * Despite being excluded by the qualifier filter, a copy of every cell
   * that reaches the sink is present in the final result.
   * As with an [Interleave][google.bigtable.v2.RowFilter.Interleave],
   * duplicate cells are possible, and appear in an unspecified mutual order.
   * In this case we have a duplicate with column "A:B" and timestamp 2,
   * because one copy passed through the all filter while the other was
   * passed through the label and sink. Note that one copy has label "foo",
   * while the other does not.
   * Cannot be used within the `predicate_filter`, `true_filter`, or
   * `false_filter` of a [Condition][google.bigtable.v2.RowFilter.Condition].
   * </pre>
   *
   * <code>optional bool sink = 16;</code>
   */
  boolean getSink();

  /**
   * <pre>
   * Matches all cells, regardless of input. Functionally equivalent to
   * leaving `filter` unset, but included for completeness.
   * </pre>
   *
   * <code>optional bool pass_all_filter = 17;</code>
   */
  boolean getPassAllFilter();

  /**
   * <pre>
   * Does not match any cells, regardless of input. Useful for temporarily
   * disabling just part of a filter.
   * </pre>
   *
   * <code>optional bool block_all_filter = 18;</code>
   */
  boolean getBlockAllFilter();

  /**
   * <pre>
   * Matches only cells from rows whose keys satisfy the given RE2 regex. In
   * other words, passes through the entire row when the key matches, and
   * otherwise produces an empty row.
   * Note that, since row keys can contain arbitrary bytes, the `&#92;C` escape
   * sequence must be used if a true wildcard is desired. The `.` character
   * will not match the new line character `&#92;n`, which may be present in a
   * binary key.
   * </pre>
   *
   * <code>optional bytes row_key_regex_filter = 4;</code>
   */
  com.google.protobuf.ByteString getRowKeyRegexFilter();

  /**
   * <pre>
   * Matches all cells from a row with probability p, and matches no cells
   * from the row with probability 1-p.
   * </pre>
   *
   * <code>optional double row_sample_filter = 14;</code>
   */
  double getRowSampleFilter();

  /**
   * <pre>
   * Matches only cells from columns whose families satisfy the given RE2
   * regex. For technical reasons, the regex must not contain the `:`
   * character, even if it is not being used as a literal.
   * Note that, since column families cannot contain the new line character
   * `&#92;n`, it is sufficient to use `.` as a full wildcard when matching
   * column family names.
   * </pre>
   *
   * <code>optional string family_name_regex_filter = 5;</code>
   */
  java.lang.String getFamilyNameRegexFilter();
  /**
   * <pre>
   * Matches only cells from columns whose families satisfy the given RE2
   * regex. For technical reasons, the regex must not contain the `:`
   * character, even if it is not being used as a literal.
   * Note that, since column families cannot contain the new line character
   * `&#92;n`, it is sufficient to use `.` as a full wildcard when matching
   * column family names.
   * </pre>
   *
   * <code>optional string family_name_regex_filter = 5;</code>
   */
  com.google.protobuf.ByteString
      getFamilyNameRegexFilterBytes();

  /**
   * <pre>
   * Matches only cells from columns whose qualifiers satisfy the given RE2
   * regex.
   * Note that, since column qualifiers can contain arbitrary bytes, the `&#92;C`
   * escape sequence must be used if a true wildcard is desired. The `.`
   * character will not match the new line character `&#92;n`, which may be
   * present in a binary qualifier.
   * </pre>
   *
   * <code>optional bytes column_qualifier_regex_filter = 6;</code>
   */
  com.google.protobuf.ByteString getColumnQualifierRegexFilter();

  /**
   * <pre>
   * Matches only cells from columns within the given range.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.ColumnRange column_range_filter = 7;</code>
   */
  com.google.bigtable.v2.ColumnRange getColumnRangeFilter();
  /**
   * <pre>
   * Matches only cells from columns within the given range.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.ColumnRange column_range_filter = 7;</code>
   */
  com.google.bigtable.v2.ColumnRangeOrBuilder getColumnRangeFilterOrBuilder();

  /**
   * <pre>
   * Matches only cells with timestamps within the given range.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.TimestampRange timestamp_range_filter = 8;</code>
   */
  com.google.bigtable.v2.TimestampRange getTimestampRangeFilter();
  /**
   * <pre>
   * Matches only cells with timestamps within the given range.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.TimestampRange timestamp_range_filter = 8;</code>
   */
  com.google.bigtable.v2.TimestampRangeOrBuilder getTimestampRangeFilterOrBuilder();

  /**
   * <pre>
   * Matches only cells with values that satisfy the given regular expression.
   * Note that, since cell values can contain arbitrary bytes, the `&#92;C` escape
   * sequence must be used if a true wildcard is desired. The `.` character
   * will not match the new line character `&#92;n`, which may be present in a
   * binary value.
   * </pre>
   *
   * <code>optional bytes value_regex_filter = 9;</code>
   */
  com.google.protobuf.ByteString getValueRegexFilter();

  /**
   * <pre>
   * Matches only cells with values that fall within the given range.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.ValueRange value_range_filter = 15;</code>
   */
  com.google.bigtable.v2.ValueRange getValueRangeFilter();
  /**
   * <pre>
   * Matches only cells with values that fall within the given range.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.ValueRange value_range_filter = 15;</code>
   */
  com.google.bigtable.v2.ValueRangeOrBuilder getValueRangeFilterOrBuilder();

  /**
   * <pre>
   * Skips the first N cells of each row, matching all subsequent cells.
   * If duplicate cells are present, as is possible when using an Interleave,
   * each copy of the cell is counted separately.
   * </pre>
   *
   * <code>optional int32 cells_per_row_offset_filter = 10;</code>
   */
  int getCellsPerRowOffsetFilter();

  /**
   * <pre>
   * Matches only the first N cells of each row.
   * If duplicate cells are present, as is possible when using an Interleave,
   * each copy of the cell is counted separately.
   * </pre>
   *
   * <code>optional int32 cells_per_row_limit_filter = 11;</code>
   */
  int getCellsPerRowLimitFilter();

  /**
   * <pre>
   * Matches only the most recent N cells within each column. For example,
   * if N=2, this filter would match column `foo:bar` at timestamps 10 and 9,
   * skip all earlier cells in `foo:bar`, and then begin matching again in
   * column `foo:bar2`.
   * If duplicate cells are present, as is possible when using an Interleave,
   * each copy of the cell is counted separately.
   * </pre>
   *
   * <code>optional int32 cells_per_column_limit_filter = 12;</code>
   */
  int getCellsPerColumnLimitFilter();

  /**
   * <pre>
   * Replaces each cell's value with the empty string.
   * </pre>
   *
   * <code>optional bool strip_value_transformer = 13;</code>
   */
  boolean getStripValueTransformer();

  /**
   * <pre>
   * Applies the given label to all cells in the output row. This allows
   * the client to determine which results were produced from which part of
   * the filter.
   * Values must be at most 15 characters in length, and match the RE2
   * pattern `[a-z0-9&#92;&#92;-]+`
   * Due to a technical limitation, it is not currently possible to apply
   * multiple labels to a cell. As a result, a Chain may have no more than
   * one sub-filter which contains a `apply_label_transformer`. It is okay for
   * an Interleave to contain multiple `apply_label_transformers`, as they
   * will be applied to separate copies of the input. This may be relaxed in
   * the future.
   * </pre>
   *
   * <code>optional string apply_label_transformer = 19;</code>
   */
  java.lang.String getApplyLabelTransformer();
  /**
   * <pre>
   * Applies the given label to all cells in the output row. This allows
   * the client to determine which results were produced from which part of
   * the filter.
   * Values must be at most 15 characters in length, and match the RE2
   * pattern `[a-z0-9&#92;&#92;-]+`
   * Due to a technical limitation, it is not currently possible to apply
   * multiple labels to a cell. As a result, a Chain may have no more than
   * one sub-filter which contains a `apply_label_transformer`. It is okay for
   * an Interleave to contain multiple `apply_label_transformers`, as they
   * will be applied to separate copies of the input. This may be relaxed in
   * the future.
   * </pre>
   *
   * <code>optional string apply_label_transformer = 19;</code>
   */
  com.google.protobuf.ByteString
      getApplyLabelTransformerBytes();

  public com.google.bigtable.v2.RowFilter.FilterCase getFilterCase();
}
