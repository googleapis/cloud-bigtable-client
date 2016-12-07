// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/admin/v2/bigtable_table_admin.proto

package com.google.bigtable.admin.v2;

public interface DropRowRangeRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.bigtable.admin.v2.DropRowRangeRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The unique name of the table on which to drop a range of rows.
   * Values are of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;/tables/&lt;table&gt;`.
   * </pre>
   *
   * <code>optional string name = 1;</code>
   */
  java.lang.String getName();
  /**
   * <pre>
   * The unique name of the table on which to drop a range of rows.
   * Values are of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;/tables/&lt;table&gt;`.
   * </pre>
   *
   * <code>optional string name = 1;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * Delete all rows that start with this row key prefix. Prefix cannot be
   * zero length.
   * </pre>
   *
   * <code>optional bytes row_key_prefix = 2;</code>
   */
  com.google.protobuf.ByteString getRowKeyPrefix();

  /**
   * <pre>
   * Delete all rows in the table. Setting this to false is a no-op.
   * </pre>
   *
   * <code>optional bool delete_all_data_from_table = 3;</code>
   */
  boolean getDeleteAllDataFromTable();

  public com.google.bigtable.admin.v2.DropRowRangeRequest.TargetCase getTargetCase();
}
