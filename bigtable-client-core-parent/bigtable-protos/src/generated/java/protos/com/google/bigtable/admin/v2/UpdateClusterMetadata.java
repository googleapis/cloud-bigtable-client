// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/admin/v2/bigtable_instance_admin.proto

package com.google.bigtable.admin.v2;

/**
 * <pre>
 * The metadata for the Operation returned by UpdateCluster.
 * </pre>
 *
 * Protobuf type {@code google.bigtable.admin.v2.UpdateClusterMetadata}
 */
public  final class UpdateClusterMetadata extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.bigtable.admin.v2.UpdateClusterMetadata)
    UpdateClusterMetadataOrBuilder {
  // Use UpdateClusterMetadata.newBuilder() to construct.
  private UpdateClusterMetadata(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UpdateClusterMetadata() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private UpdateClusterMetadata(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            com.google.bigtable.admin.v2.Cluster.Builder subBuilder = null;
            if (originalRequest_ != null) {
              subBuilder = originalRequest_.toBuilder();
            }
            originalRequest_ = input.readMessage(com.google.bigtable.admin.v2.Cluster.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(originalRequest_);
              originalRequest_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            com.google.protobuf.Timestamp.Builder subBuilder = null;
            if (requestTime_ != null) {
              subBuilder = requestTime_.toBuilder();
            }
            requestTime_ = input.readMessage(com.google.protobuf.Timestamp.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(requestTime_);
              requestTime_ = subBuilder.buildPartial();
            }

            break;
          }
          case 26: {
            com.google.protobuf.Timestamp.Builder subBuilder = null;
            if (finishTime_ != null) {
              subBuilder = finishTime_.toBuilder();
            }
            finishTime_ = input.readMessage(com.google.protobuf.Timestamp.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(finishTime_);
              finishTime_ = subBuilder.buildPartial();
            }

            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.bigtable.admin.v2.BigtableInstanceAdminProto.internal_static_google_bigtable_admin_v2_UpdateClusterMetadata_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.bigtable.admin.v2.BigtableInstanceAdminProto.internal_static_google_bigtable_admin_v2_UpdateClusterMetadata_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.bigtable.admin.v2.UpdateClusterMetadata.class, com.google.bigtable.admin.v2.UpdateClusterMetadata.Builder.class);
  }

  public static final int ORIGINAL_REQUEST_FIELD_NUMBER = 1;
  private com.google.bigtable.admin.v2.Cluster originalRequest_;
  /**
   * <pre>
   * The request that prompted the initiation of this UpdateCluster operation.
   * </pre>
   *
   * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
   */
  public boolean hasOriginalRequest() {
    return originalRequest_ != null;
  }
  /**
   * <pre>
   * The request that prompted the initiation of this UpdateCluster operation.
   * </pre>
   *
   * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
   */
  public com.google.bigtable.admin.v2.Cluster getOriginalRequest() {
    return originalRequest_ == null ? com.google.bigtable.admin.v2.Cluster.getDefaultInstance() : originalRequest_;
  }
  /**
   * <pre>
   * The request that prompted the initiation of this UpdateCluster operation.
   * </pre>
   *
   * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
   */
  public com.google.bigtable.admin.v2.ClusterOrBuilder getOriginalRequestOrBuilder() {
    return getOriginalRequest();
  }

  public static final int REQUEST_TIME_FIELD_NUMBER = 2;
  private com.google.protobuf.Timestamp requestTime_;
  /**
   * <pre>
   * The time at which the original request was received.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp request_time = 2;</code>
   */
  public boolean hasRequestTime() {
    return requestTime_ != null;
  }
  /**
   * <pre>
   * The time at which the original request was received.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp request_time = 2;</code>
   */
  public com.google.protobuf.Timestamp getRequestTime() {
    return requestTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : requestTime_;
  }
  /**
   * <pre>
   * The time at which the original request was received.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp request_time = 2;</code>
   */
  public com.google.protobuf.TimestampOrBuilder getRequestTimeOrBuilder() {
    return getRequestTime();
  }

  public static final int FINISH_TIME_FIELD_NUMBER = 3;
  private com.google.protobuf.Timestamp finishTime_;
  /**
   * <pre>
   * The time at which the operation failed or was completed successfully.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp finish_time = 3;</code>
   */
  public boolean hasFinishTime() {
    return finishTime_ != null;
  }
  /**
   * <pre>
   * The time at which the operation failed or was completed successfully.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp finish_time = 3;</code>
   */
  public com.google.protobuf.Timestamp getFinishTime() {
    return finishTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : finishTime_;
  }
  /**
   * <pre>
   * The time at which the operation failed or was completed successfully.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp finish_time = 3;</code>
   */
  public com.google.protobuf.TimestampOrBuilder getFinishTimeOrBuilder() {
    return getFinishTime();
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (originalRequest_ != null) {
      output.writeMessage(1, getOriginalRequest());
    }
    if (requestTime_ != null) {
      output.writeMessage(2, getRequestTime());
    }
    if (finishTime_ != null) {
      output.writeMessage(3, getFinishTime());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (originalRequest_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getOriginalRequest());
    }
    if (requestTime_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getRequestTime());
    }
    if (finishTime_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getFinishTime());
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.google.bigtable.admin.v2.UpdateClusterMetadata)) {
      return super.equals(obj);
    }
    com.google.bigtable.admin.v2.UpdateClusterMetadata other = (com.google.bigtable.admin.v2.UpdateClusterMetadata) obj;

    boolean result = true;
    result = result && (hasOriginalRequest() == other.hasOriginalRequest());
    if (hasOriginalRequest()) {
      result = result && getOriginalRequest()
          .equals(other.getOriginalRequest());
    }
    result = result && (hasRequestTime() == other.hasRequestTime());
    if (hasRequestTime()) {
      result = result && getRequestTime()
          .equals(other.getRequestTime());
    }
    result = result && (hasFinishTime() == other.hasFinishTime());
    if (hasFinishTime()) {
      result = result && getFinishTime()
          .equals(other.getFinishTime());
    }
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasOriginalRequest()) {
      hash = (37 * hash) + ORIGINAL_REQUEST_FIELD_NUMBER;
      hash = (53 * hash) + getOriginalRequest().hashCode();
    }
    if (hasRequestTime()) {
      hash = (37 * hash) + REQUEST_TIME_FIELD_NUMBER;
      hash = (53 * hash) + getRequestTime().hashCode();
    }
    if (hasFinishTime()) {
      hash = (37 * hash) + FINISH_TIME_FIELD_NUMBER;
      hash = (53 * hash) + getFinishTime().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.bigtable.admin.v2.UpdateClusterMetadata parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.bigtable.admin.v2.UpdateClusterMetadata prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * The metadata for the Operation returned by UpdateCluster.
   * </pre>
   *
   * Protobuf type {@code google.bigtable.admin.v2.UpdateClusterMetadata}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.bigtable.admin.v2.UpdateClusterMetadata)
      com.google.bigtable.admin.v2.UpdateClusterMetadataOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.bigtable.admin.v2.BigtableInstanceAdminProto.internal_static_google_bigtable_admin_v2_UpdateClusterMetadata_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.bigtable.admin.v2.BigtableInstanceAdminProto.internal_static_google_bigtable_admin_v2_UpdateClusterMetadata_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.bigtable.admin.v2.UpdateClusterMetadata.class, com.google.bigtable.admin.v2.UpdateClusterMetadata.Builder.class);
    }

    // Construct using com.google.bigtable.admin.v2.UpdateClusterMetadata.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      if (originalRequestBuilder_ == null) {
        originalRequest_ = null;
      } else {
        originalRequest_ = null;
        originalRequestBuilder_ = null;
      }
      if (requestTimeBuilder_ == null) {
        requestTime_ = null;
      } else {
        requestTime_ = null;
        requestTimeBuilder_ = null;
      }
      if (finishTimeBuilder_ == null) {
        finishTime_ = null;
      } else {
        finishTime_ = null;
        finishTimeBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.bigtable.admin.v2.BigtableInstanceAdminProto.internal_static_google_bigtable_admin_v2_UpdateClusterMetadata_descriptor;
    }

    public com.google.bigtable.admin.v2.UpdateClusterMetadata getDefaultInstanceForType() {
      return com.google.bigtable.admin.v2.UpdateClusterMetadata.getDefaultInstance();
    }

    public com.google.bigtable.admin.v2.UpdateClusterMetadata build() {
      com.google.bigtable.admin.v2.UpdateClusterMetadata result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.google.bigtable.admin.v2.UpdateClusterMetadata buildPartial() {
      com.google.bigtable.admin.v2.UpdateClusterMetadata result = new com.google.bigtable.admin.v2.UpdateClusterMetadata(this);
      if (originalRequestBuilder_ == null) {
        result.originalRequest_ = originalRequest_;
      } else {
        result.originalRequest_ = originalRequestBuilder_.build();
      }
      if (requestTimeBuilder_ == null) {
        result.requestTime_ = requestTime_;
      } else {
        result.requestTime_ = requestTimeBuilder_.build();
      }
      if (finishTimeBuilder_ == null) {
        result.finishTime_ = finishTime_;
      } else {
        result.finishTime_ = finishTimeBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.bigtable.admin.v2.UpdateClusterMetadata) {
        return mergeFrom((com.google.bigtable.admin.v2.UpdateClusterMetadata)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.bigtable.admin.v2.UpdateClusterMetadata other) {
      if (other == com.google.bigtable.admin.v2.UpdateClusterMetadata.getDefaultInstance()) return this;
      if (other.hasOriginalRequest()) {
        mergeOriginalRequest(other.getOriginalRequest());
      }
      if (other.hasRequestTime()) {
        mergeRequestTime(other.getRequestTime());
      }
      if (other.hasFinishTime()) {
        mergeFinishTime(other.getFinishTime());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.google.bigtable.admin.v2.UpdateClusterMetadata parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.google.bigtable.admin.v2.UpdateClusterMetadata) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.bigtable.admin.v2.Cluster originalRequest_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.bigtable.admin.v2.Cluster, com.google.bigtable.admin.v2.Cluster.Builder, com.google.bigtable.admin.v2.ClusterOrBuilder> originalRequestBuilder_;
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    public boolean hasOriginalRequest() {
      return originalRequestBuilder_ != null || originalRequest_ != null;
    }
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    public com.google.bigtable.admin.v2.Cluster getOriginalRequest() {
      if (originalRequestBuilder_ == null) {
        return originalRequest_ == null ? com.google.bigtable.admin.v2.Cluster.getDefaultInstance() : originalRequest_;
      } else {
        return originalRequestBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    public Builder setOriginalRequest(com.google.bigtable.admin.v2.Cluster value) {
      if (originalRequestBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        originalRequest_ = value;
        onChanged();
      } else {
        originalRequestBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    public Builder setOriginalRequest(
        com.google.bigtable.admin.v2.Cluster.Builder builderForValue) {
      if (originalRequestBuilder_ == null) {
        originalRequest_ = builderForValue.build();
        onChanged();
      } else {
        originalRequestBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    public Builder mergeOriginalRequest(com.google.bigtable.admin.v2.Cluster value) {
      if (originalRequestBuilder_ == null) {
        if (originalRequest_ != null) {
          originalRequest_ =
            com.google.bigtable.admin.v2.Cluster.newBuilder(originalRequest_).mergeFrom(value).buildPartial();
        } else {
          originalRequest_ = value;
        }
        onChanged();
      } else {
        originalRequestBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    public Builder clearOriginalRequest() {
      if (originalRequestBuilder_ == null) {
        originalRequest_ = null;
        onChanged();
      } else {
        originalRequest_ = null;
        originalRequestBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    public com.google.bigtable.admin.v2.Cluster.Builder getOriginalRequestBuilder() {
      
      onChanged();
      return getOriginalRequestFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    public com.google.bigtable.admin.v2.ClusterOrBuilder getOriginalRequestOrBuilder() {
      if (originalRequestBuilder_ != null) {
        return originalRequestBuilder_.getMessageOrBuilder();
      } else {
        return originalRequest_ == null ?
            com.google.bigtable.admin.v2.Cluster.getDefaultInstance() : originalRequest_;
      }
    }
    /**
     * <pre>
     * The request that prompted the initiation of this UpdateCluster operation.
     * </pre>
     *
     * <code>.google.bigtable.admin.v2.Cluster original_request = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.bigtable.admin.v2.Cluster, com.google.bigtable.admin.v2.Cluster.Builder, com.google.bigtable.admin.v2.ClusterOrBuilder> 
        getOriginalRequestFieldBuilder() {
      if (originalRequestBuilder_ == null) {
        originalRequestBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.bigtable.admin.v2.Cluster, com.google.bigtable.admin.v2.Cluster.Builder, com.google.bigtable.admin.v2.ClusterOrBuilder>(
                getOriginalRequest(),
                getParentForChildren(),
                isClean());
        originalRequest_ = null;
      }
      return originalRequestBuilder_;
    }

    private com.google.protobuf.Timestamp requestTime_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> requestTimeBuilder_;
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    public boolean hasRequestTime() {
      return requestTimeBuilder_ != null || requestTime_ != null;
    }
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    public com.google.protobuf.Timestamp getRequestTime() {
      if (requestTimeBuilder_ == null) {
        return requestTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : requestTime_;
      } else {
        return requestTimeBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    public Builder setRequestTime(com.google.protobuf.Timestamp value) {
      if (requestTimeBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        requestTime_ = value;
        onChanged();
      } else {
        requestTimeBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    public Builder setRequestTime(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      if (requestTimeBuilder_ == null) {
        requestTime_ = builderForValue.build();
        onChanged();
      } else {
        requestTimeBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    public Builder mergeRequestTime(com.google.protobuf.Timestamp value) {
      if (requestTimeBuilder_ == null) {
        if (requestTime_ != null) {
          requestTime_ =
            com.google.protobuf.Timestamp.newBuilder(requestTime_).mergeFrom(value).buildPartial();
        } else {
          requestTime_ = value;
        }
        onChanged();
      } else {
        requestTimeBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    public Builder clearRequestTime() {
      if (requestTimeBuilder_ == null) {
        requestTime_ = null;
        onChanged();
      } else {
        requestTime_ = null;
        requestTimeBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    public com.google.protobuf.Timestamp.Builder getRequestTimeBuilder() {
      
      onChanged();
      return getRequestTimeFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getRequestTimeOrBuilder() {
      if (requestTimeBuilder_ != null) {
        return requestTimeBuilder_.getMessageOrBuilder();
      } else {
        return requestTime_ == null ?
            com.google.protobuf.Timestamp.getDefaultInstance() : requestTime_;
      }
    }
    /**
     * <pre>
     * The time at which the original request was received.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp request_time = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
        getRequestTimeFieldBuilder() {
      if (requestTimeBuilder_ == null) {
        requestTimeBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                getRequestTime(),
                getParentForChildren(),
                isClean());
        requestTime_ = null;
      }
      return requestTimeBuilder_;
    }

    private com.google.protobuf.Timestamp finishTime_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> finishTimeBuilder_;
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    public boolean hasFinishTime() {
      return finishTimeBuilder_ != null || finishTime_ != null;
    }
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    public com.google.protobuf.Timestamp getFinishTime() {
      if (finishTimeBuilder_ == null) {
        return finishTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : finishTime_;
      } else {
        return finishTimeBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    public Builder setFinishTime(com.google.protobuf.Timestamp value) {
      if (finishTimeBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        finishTime_ = value;
        onChanged();
      } else {
        finishTimeBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    public Builder setFinishTime(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      if (finishTimeBuilder_ == null) {
        finishTime_ = builderForValue.build();
        onChanged();
      } else {
        finishTimeBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    public Builder mergeFinishTime(com.google.protobuf.Timestamp value) {
      if (finishTimeBuilder_ == null) {
        if (finishTime_ != null) {
          finishTime_ =
            com.google.protobuf.Timestamp.newBuilder(finishTime_).mergeFrom(value).buildPartial();
        } else {
          finishTime_ = value;
        }
        onChanged();
      } else {
        finishTimeBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    public Builder clearFinishTime() {
      if (finishTimeBuilder_ == null) {
        finishTime_ = null;
        onChanged();
      } else {
        finishTime_ = null;
        finishTimeBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    public com.google.protobuf.Timestamp.Builder getFinishTimeBuilder() {
      
      onChanged();
      return getFinishTimeFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getFinishTimeOrBuilder() {
      if (finishTimeBuilder_ != null) {
        return finishTimeBuilder_.getMessageOrBuilder();
      } else {
        return finishTime_ == null ?
            com.google.protobuf.Timestamp.getDefaultInstance() : finishTime_;
      }
    }
    /**
     * <pre>
     * The time at which the operation failed or was completed successfully.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp finish_time = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
        getFinishTimeFieldBuilder() {
      if (finishTimeBuilder_ == null) {
        finishTimeBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                getFinishTime(),
                getParentForChildren(),
                isClean());
        finishTime_ = null;
      }
      return finishTimeBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:google.bigtable.admin.v2.UpdateClusterMetadata)
  }

  // @@protoc_insertion_point(class_scope:google.bigtable.admin.v2.UpdateClusterMetadata)
  private static final com.google.bigtable.admin.v2.UpdateClusterMetadata DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.bigtable.admin.v2.UpdateClusterMetadata();
  }

  public static com.google.bigtable.admin.v2.UpdateClusterMetadata getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UpdateClusterMetadata>
      PARSER = new com.google.protobuf.AbstractParser<UpdateClusterMetadata>() {
    public UpdateClusterMetadata parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new UpdateClusterMetadata(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<UpdateClusterMetadata> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UpdateClusterMetadata> getParserForType() {
    return PARSER;
  }

  public com.google.bigtable.admin.v2.UpdateClusterMetadata getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

