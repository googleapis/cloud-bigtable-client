// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/type/timeofday.proto

package com.google.type;

public final class TimeOfDayProto {
  private TimeOfDayProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_google_type_TimeOfDay_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_google_type_TimeOfDay_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\033google/type/timeofday.proto\022\013google.ty" +
      "pe\"K\n\tTimeOfDay\022\r\n\005hours\030\001 \001(\005\022\017\n\007minute" +
      "s\030\002 \001(\005\022\017\n\007seconds\030\003 \001(\005\022\r\n\005nanos\030\004 \001(\005B" +
      ",\n\017com.google.typeB\016TimeOfDayProtoP\001\240\001\001\242" +
      "\002\003GTPb\006proto3"
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
        }, assigner);
    internal_static_google_type_TimeOfDay_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_google_type_TimeOfDay_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_google_type_TimeOfDay_descriptor,
        new java.lang.String[] { "Hours", "Minutes", "Seconds", "Nanos", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
