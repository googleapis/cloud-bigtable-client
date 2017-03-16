// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/admin/v2/bigtable_table_admin.proto

package com.google.bigtable.admin.v2;

public interface ModifyColumnFamiliesRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.bigtable.admin.v2.ModifyColumnFamiliesRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The unique name of the table whose families should be modified.
   * Values are of the form projects/&lt;project&gt;/instances/&lt;instance&gt;/tables/&lt;table&gt;
   * </pre>
   *
   * <code>string name = 1;</code>
   */
  java.lang.String getName();
  /**
   * <pre>
   * The unique name of the table whose families should be modified.
   * Values are of the form projects/&lt;project&gt;/instances/&lt;instance&gt;/tables/&lt;table&gt;
   * </pre>
   *
   * <code>string name = 1;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * Modifications to be atomically applied to the specified table's families.
   * Entries are applied in order, meaning that earlier modifications can be
   * masked by later ones (in the case of repeated updates to the same family,
   * for example).
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.ModifyColumnFamiliesRequest.Modification modifications = 2;</code>
   */
  java.util.List<com.google.bigtable.admin.v2.ModifyColumnFamiliesRequest.Modification> 
      getModificationsList();
  /**
   * <pre>
   * Modifications to be atomically applied to the specified table's families.
   * Entries are applied in order, meaning that earlier modifications can be
   * masked by later ones (in the case of repeated updates to the same family,
   * for example).
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.ModifyColumnFamiliesRequest.Modification modifications = 2;</code>
   */
  com.google.bigtable.admin.v2.ModifyColumnFamiliesRequest.Modification getModifications(int index);
  /**
   * <pre>
   * Modifications to be atomically applied to the specified table's families.
   * Entries are applied in order, meaning that earlier modifications can be
   * masked by later ones (in the case of repeated updates to the same family,
   * for example).
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.ModifyColumnFamiliesRequest.Modification modifications = 2;</code>
   */
  int getModificationsCount();
  /**
   * <pre>
   * Modifications to be atomically applied to the specified table's families.
   * Entries are applied in order, meaning that earlier modifications can be
   * masked by later ones (in the case of repeated updates to the same family,
   * for example).
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.ModifyColumnFamiliesRequest.Modification modifications = 2;</code>
   */
  java.util.List<? extends com.google.bigtable.admin.v2.ModifyColumnFamiliesRequest.ModificationOrBuilder> 
      getModificationsOrBuilderList();
  /**
   * <pre>
   * Modifications to be atomically applied to the specified table's families.
   * Entries are applied in order, meaning that earlier modifications can be
   * masked by later ones (in the case of repeated updates to the same family,
   * for example).
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.ModifyColumnFamiliesRequest.Modification modifications = 2;</code>
   */
  com.google.bigtable.admin.v2.ModifyColumnFamiliesRequest.ModificationOrBuilder getModificationsOrBuilder(
      int index);
}
