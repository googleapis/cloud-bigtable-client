// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/v2/bigtable.proto

package com.google.bigtable.v2;

public interface ReadRowsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.bigtable.v2.ReadRowsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The unique name of the table from which to read.
   * Values are of the form
   * projects/&amp;lt;project&amp;gt;/instances/&amp;lt;instance&amp;gt;/tables/&amp;lt;table&amp;gt;
   * </pre>
   *
   * <code>optional string table_name = 1;</code>
   */
  java.lang.String getTableName();
  /**
   * <pre>
   * The unique name of the table from which to read.
   * Values are of the form
   * projects/&amp;lt;project&amp;gt;/instances/&amp;lt;instance&amp;gt;/tables/&amp;lt;table&amp;gt;
   * </pre>
   *
   * <code>optional string table_name = 1;</code>
   */
  com.google.protobuf.ByteString
      getTableNameBytes();

  /**
   * <pre>
   * The row keys and/or ranges to read. If not specified, reads from all rows.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowSet rows = 2;</code>
   */
  boolean hasRows();
  /**
   * <pre>
   * The row keys and/or ranges to read. If not specified, reads from all rows.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowSet rows = 2;</code>
   */
  com.google.bigtable.v2.RowSet getRows();
  /**
   * <pre>
   * The row keys and/or ranges to read. If not specified, reads from all rows.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowSet rows = 2;</code>
   */
  com.google.bigtable.v2.RowSetOrBuilder getRowsOrBuilder();

  /**
   * <pre>
   * The filter to apply to the contents of the specified row(s). If unset,
   * reads the entirety of each row.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter filter = 3;</code>
   */
  boolean hasFilter();
  /**
   * <pre>
   * The filter to apply to the contents of the specified row(s). If unset,
   * reads the entirety of each row.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter filter = 3;</code>
   */
  com.google.bigtable.v2.RowFilter getFilter();
  /**
   * <pre>
   * The filter to apply to the contents of the specified row(s). If unset,
   * reads the entirety of each row.
   * </pre>
   *
   * <code>optional .google.bigtable.v2.RowFilter filter = 3;</code>
   */
  com.google.bigtable.v2.RowFilterOrBuilder getFilterOrBuilder();

  /**
   * <pre>
   * The read will terminate after committing to N rows' worth of results. The
   * default (zero) is to return all results.
   * </pre>
   *
   * <code>optional int64 rows_limit = 4;</code>
   */
  long getRowsLimit();
}
