// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/admin/v2/bigtable_instance_admin.proto

package com.google.bigtable.admin.v2;

public interface ListInstancesResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.bigtable.admin.v2.ListInstancesResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The list of requested instances.
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.Instance instances = 1;</code>
   */
  java.util.List<com.google.bigtable.admin.v2.Instance> 
      getInstancesList();
  /**
   * <pre>
   * The list of requested instances.
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.Instance instances = 1;</code>
   */
  com.google.bigtable.admin.v2.Instance getInstances(int index);
  /**
   * <pre>
   * The list of requested instances.
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.Instance instances = 1;</code>
   */
  int getInstancesCount();
  /**
   * <pre>
   * The list of requested instances.
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.Instance instances = 1;</code>
   */
  java.util.List<? extends com.google.bigtable.admin.v2.InstanceOrBuilder> 
      getInstancesOrBuilderList();
  /**
   * <pre>
   * The list of requested instances.
   * </pre>
   *
   * <code>repeated .google.bigtable.admin.v2.Instance instances = 1;</code>
   */
  com.google.bigtable.admin.v2.InstanceOrBuilder getInstancesOrBuilder(
      int index);

  /**
   * <pre>
   * Locations from which Instance information could not be retrieved,
   * due to an outage or some other transient condition.
   * Instances whose Clusters are all in one of the failed locations
   * may be missing from `instances`, and Instances with at least one
   * Cluster in a failed location may only have partial information returned.
   * </pre>
   *
   * <code>repeated string failed_locations = 2;</code>
   */
  java.util.List<java.lang.String>
      getFailedLocationsList();
  /**
   * <pre>
   * Locations from which Instance information could not be retrieved,
   * due to an outage or some other transient condition.
   * Instances whose Clusters are all in one of the failed locations
   * may be missing from `instances`, and Instances with at least one
   * Cluster in a failed location may only have partial information returned.
   * </pre>
   *
   * <code>repeated string failed_locations = 2;</code>
   */
  int getFailedLocationsCount();
  /**
   * <pre>
   * Locations from which Instance information could not be retrieved,
   * due to an outage or some other transient condition.
   * Instances whose Clusters are all in one of the failed locations
   * may be missing from `instances`, and Instances with at least one
   * Cluster in a failed location may only have partial information returned.
   * </pre>
   *
   * <code>repeated string failed_locations = 2;</code>
   */
  java.lang.String getFailedLocations(int index);
  /**
   * <pre>
   * Locations from which Instance information could not be retrieved,
   * due to an outage or some other transient condition.
   * Instances whose Clusters are all in one of the failed locations
   * may be missing from `instances`, and Instances with at least one
   * Cluster in a failed location may only have partial information returned.
   * </pre>
   *
   * <code>repeated string failed_locations = 2;</code>
   */
  com.google.protobuf.ByteString
      getFailedLocationsBytes(int index);

  /**
   * <pre>
   * Set if not all instances could be returned in a single response.
   * Pass this value to `page_token` in another request to get the next
   * page of results.
   * </pre>
   *
   * <code>optional string next_page_token = 3;</code>
   */
  java.lang.String getNextPageToken();
  /**
   * <pre>
   * Set if not all instances could be returned in a single response.
   * Pass this value to `page_token` in another request to get the next
   * page of results.
   * </pre>
   *
   * <code>optional string next_page_token = 3;</code>
   */
  com.google.protobuf.ByteString
      getNextPageTokenBytes();
}
