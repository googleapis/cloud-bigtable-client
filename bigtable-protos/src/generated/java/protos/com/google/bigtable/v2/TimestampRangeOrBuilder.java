// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/v2/data.proto

package com.google.bigtable.v2;

public interface TimestampRangeOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.bigtable.v2.TimestampRange)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional int64 start_timestamp_micros = 1;</code>
   *
   * <pre>
   * Inclusive lower bound. If left empty, interpreted as 0.
   * </pre>
   */
  long getStartTimestampMicros();

  /**
   * <code>optional int64 end_timestamp_micros = 2;</code>
   *
   * <pre>
   * Exclusive upper bound. If left empty, interpreted as infinity.
   * </pre>
   */
  long getEndTimestampMicros();
}
