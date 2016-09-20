// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/admin/table/v1/bigtable_table_service_messages.proto

package com.google.bigtable.admin.table.v1;

public final class BigtableTableServiceMessagesProto {
  private BigtableTableServiceMessagesProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_CreateTableRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_CreateTableRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_ListTablesRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_ListTablesRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_ListTablesResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_ListTablesResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_GetTableRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_GetTableRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_DeleteTableRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_DeleteTableRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_RenameTableRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_RenameTableRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_CreateColumnFamilyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_CreateColumnFamilyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_DeleteColumnFamilyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_DeleteColumnFamilyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_bigtable_admin_table_v1_BulkDeleteRowsRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_bigtable_admin_table_v1_BulkDeleteRowsRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\nDgoogle/bigtable/admin/table/v1/bigtabl" +
      "e_table_service_messages.proto\022\036google.b" +
      "igtable.admin.table.v1\0328google/bigtable/" +
      "admin/table/v1/bigtable_table_data.proto" +
      "\"\206\001\n\022CreateTableRequest\022\014\n\004name\030\001 \001(\t\022\020\n" +
      "\010table_id\030\002 \001(\t\0224\n\005table\030\003 \001(\0132%.google." +
      "bigtable.admin.table.v1.Table\022\032\n\022initial" +
      "_split_keys\030\004 \003(\t\"!\n\021ListTablesRequest\022\014" +
      "\n\004name\030\001 \001(\t\"K\n\022ListTablesResponse\0225\n\006ta" +
      "bles\030\001 \003(\0132%.google.bigtable.admin.table",
      ".v1.Table\"\037\n\017GetTableRequest\022\014\n\004name\030\001 \001" +
      "(\t\"\"\n\022DeleteTableRequest\022\014\n\004name\030\001 \001(\t\"2" +
      "\n\022RenameTableRequest\022\014\n\004name\030\001 \001(\t\022\016\n\006ne" +
      "w_id\030\002 \001(\t\"\210\001\n\031CreateColumnFamilyRequest" +
      "\022\014\n\004name\030\001 \001(\t\022\030\n\020column_family_id\030\002 \001(\t" +
      "\022C\n\rcolumn_family\030\003 \001(\0132,.google.bigtabl" +
      "e.admin.table.v1.ColumnFamily\")\n\031DeleteC" +
      "olumnFamilyRequest\022\014\n\004name\030\001 \001(\t\"u\n\025Bulk" +
      "DeleteRowsRequest\022\022\n\ntable_name\030\001 \001(\t\022\030\n" +
      "\016row_key_prefix\030\002 \001(\014H\000\022$\n\032delete_all_da",
      "ta_from_table\030\003 \001(\010H\000B\010\n\006targetBI\n\"com.g" +
      "oogle.bigtable.admin.table.v1B!BigtableT" +
      "ableServiceMessagesProtoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.bigtable.admin.table.v1.BigtableTableDataProto.getDescriptor(),
        }, assigner);
    internal_static_google_bigtable_admin_table_v1_CreateTableRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_google_bigtable_admin_table_v1_CreateTableRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_CreateTableRequest_descriptor,
        new java.lang.String[] { "Name", "TableId", "Table", "InitialSplitKeys", });
    internal_static_google_bigtable_admin_table_v1_ListTablesRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_google_bigtable_admin_table_v1_ListTablesRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_ListTablesRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_google_bigtable_admin_table_v1_ListTablesResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_google_bigtable_admin_table_v1_ListTablesResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_ListTablesResponse_descriptor,
        new java.lang.String[] { "Tables", });
    internal_static_google_bigtable_admin_table_v1_GetTableRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_google_bigtable_admin_table_v1_GetTableRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_GetTableRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_google_bigtable_admin_table_v1_DeleteTableRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_google_bigtable_admin_table_v1_DeleteTableRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_DeleteTableRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_google_bigtable_admin_table_v1_RenameTableRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_google_bigtable_admin_table_v1_RenameTableRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_RenameTableRequest_descriptor,
        new java.lang.String[] { "Name", "NewId", });
    internal_static_google_bigtable_admin_table_v1_CreateColumnFamilyRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_google_bigtable_admin_table_v1_CreateColumnFamilyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_CreateColumnFamilyRequest_descriptor,
        new java.lang.String[] { "Name", "ColumnFamilyId", "ColumnFamily", });
    internal_static_google_bigtable_admin_table_v1_DeleteColumnFamilyRequest_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_google_bigtable_admin_table_v1_DeleteColumnFamilyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_DeleteColumnFamilyRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_google_bigtable_admin_table_v1_BulkDeleteRowsRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_google_bigtable_admin_table_v1_BulkDeleteRowsRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_bigtable_admin_table_v1_BulkDeleteRowsRequest_descriptor,
        new java.lang.String[] { "TableName", "RowKeyPrefix", "DeleteAllDataFromTable", "Target", });
    com.google.bigtable.admin.table.v1.BigtableTableDataProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
