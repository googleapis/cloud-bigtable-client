// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/bigtable/v1/bigtable_data.proto

package com.google.bigtable.v1;

/**
 * Protobuf type {@code google.bigtable.v1.RowRange}
 *
 * <pre>
 * Specifies a contiguous range of rows.
 * </pre>
 */
public  final class RowRange extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:google.bigtable.v1.RowRange)
    RowRangeOrBuilder {
  // Use RowRange.newBuilder() to construct.
  private RowRange(com.google.protobuf.GeneratedMessage.Builder builder) {
    super(builder);
  }
  private RowRange() {
    startKey_ = com.google.protobuf.ByteString.EMPTY;
    endKey_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private RowRange(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry) {
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
          case 18: {

            startKey_ = input.readBytes();
            break;
          }
          case 26: {

            endKey_ = input.readBytes();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw new RuntimeException(e.setUnfinishedMessage(this));
    } catch (java.io.IOException e) {
      throw new RuntimeException(
          new com.google.protobuf.InvalidProtocolBufferException(
              e.getMessage()).setUnfinishedMessage(this));
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.bigtable.v1.BigtableDataProto.internal_static_google_bigtable_v1_RowRange_descriptor;
  }

  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.bigtable.v1.BigtableDataProto.internal_static_google_bigtable_v1_RowRange_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.bigtable.v1.RowRange.class, com.google.bigtable.v1.RowRange.Builder.class);
  }

  public static final int START_KEY_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString startKey_;
  /**
   * <code>optional bytes start_key = 2;</code>
   *
   * <pre>
   * Inclusive lower bound. If left empty, interpreted as the empty string.
   * </pre>
   */
  public com.google.protobuf.ByteString getStartKey() {
    return startKey_;
  }

  public static final int END_KEY_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString endKey_;
  /**
   * <code>optional bytes end_key = 3;</code>
   *
   * <pre>
   * Exclusive upper bound. If left empty, interpreted as infinity.
   * </pre>
   */
  public com.google.protobuf.ByteString getEndKey() {
    return endKey_;
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
    if (!startKey_.isEmpty()) {
      output.writeBytes(2, startKey_);
    }
    if (!endKey_.isEmpty()) {
      output.writeBytes(3, endKey_);
    }
  }

  private int memoizedSerializedSize = -1;
  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (!startKey_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, startKey_);
    }
    if (!endKey_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, endKey_);
    }
    memoizedSerializedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  public static com.google.bigtable.v1.RowRange parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.bigtable.v1.RowRange parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.bigtable.v1.RowRange parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.bigtable.v1.RowRange parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.bigtable.v1.RowRange parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static com.google.bigtable.v1.RowRange parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }
  public static com.google.bigtable.v1.RowRange parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input);
  }
  public static com.google.bigtable.v1.RowRange parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input, extensionRegistry);
  }
  public static com.google.bigtable.v1.RowRange parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static com.google.bigtable.v1.RowRange parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.bigtable.v1.RowRange prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code google.bigtable.v1.RowRange}
   *
   * <pre>
   * Specifies a contiguous range of rows.
   * </pre>
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.bigtable.v1.RowRange)
      com.google.bigtable.v1.RowRangeOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.bigtable.v1.BigtableDataProto.internal_static_google_bigtable_v1_RowRange_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.bigtable.v1.BigtableDataProto.internal_static_google_bigtable_v1_RowRange_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.bigtable.v1.RowRange.class, com.google.bigtable.v1.RowRange.Builder.class);
    }

    // Construct using com.google.bigtable.v1.RowRange.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      startKey_ = com.google.protobuf.ByteString.EMPTY;

      endKey_ = com.google.protobuf.ByteString.EMPTY;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.bigtable.v1.BigtableDataProto.internal_static_google_bigtable_v1_RowRange_descriptor;
    }

    public com.google.bigtable.v1.RowRange getDefaultInstanceForType() {
      return com.google.bigtable.v1.RowRange.getDefaultInstance();
    }

    public com.google.bigtable.v1.RowRange build() {
      com.google.bigtable.v1.RowRange result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.google.bigtable.v1.RowRange buildPartial() {
      com.google.bigtable.v1.RowRange result = new com.google.bigtable.v1.RowRange(this);
      result.startKey_ = startKey_;
      result.endKey_ = endKey_;
      onBuilt();
      return result;
    }

    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.bigtable.v1.RowRange) {
        return mergeFrom((com.google.bigtable.v1.RowRange)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.bigtable.v1.RowRange other) {
      if (other == com.google.bigtable.v1.RowRange.getDefaultInstance()) return this;
      if (other.getStartKey() != com.google.protobuf.ByteString.EMPTY) {
        setStartKey(other.getStartKey());
      }
      if (other.getEndKey() != com.google.protobuf.ByteString.EMPTY) {
        setEndKey(other.getEndKey());
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
      com.google.bigtable.v1.RowRange parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.google.bigtable.v1.RowRange) e.getUnfinishedMessage();
        throw e;
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.protobuf.ByteString startKey_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>optional bytes start_key = 2;</code>
     *
     * <pre>
     * Inclusive lower bound. If left empty, interpreted as the empty string.
     * </pre>
     */
    public com.google.protobuf.ByteString getStartKey() {
      return startKey_;
    }
    /**
     * <code>optional bytes start_key = 2;</code>
     *
     * <pre>
     * Inclusive lower bound. If left empty, interpreted as the empty string.
     * </pre>
     */
    public Builder setStartKey(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      startKey_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bytes start_key = 2;</code>
     *
     * <pre>
     * Inclusive lower bound. If left empty, interpreted as the empty string.
     * </pre>
     */
    public Builder clearStartKey() {
      
      startKey_ = getDefaultInstance().getStartKey();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString endKey_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>optional bytes end_key = 3;</code>
     *
     * <pre>
     * Exclusive upper bound. If left empty, interpreted as infinity.
     * </pre>
     */
    public com.google.protobuf.ByteString getEndKey() {
      return endKey_;
    }
    /**
     * <code>optional bytes end_key = 3;</code>
     *
     * <pre>
     * Exclusive upper bound. If left empty, interpreted as infinity.
     * </pre>
     */
    public Builder setEndKey(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      endKey_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bytes end_key = 3;</code>
     *
     * <pre>
     * Exclusive upper bound. If left empty, interpreted as infinity.
     * </pre>
     */
    public Builder clearEndKey() {
      
      endKey_ = getDefaultInstance().getEndKey();
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:google.bigtable.v1.RowRange)
  }

  // @@protoc_insertion_point(class_scope:google.bigtable.v1.RowRange)
  private static final com.google.bigtable.v1.RowRange DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.bigtable.v1.RowRange();
  }

  public static com.google.bigtable.v1.RowRange getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public static final com.google.protobuf.Parser<RowRange> PARSER =
      new com.google.protobuf.AbstractParser<RowRange>() {
    public RowRange parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      try {
        return new RowRange(input, extensionRegistry);
      } catch (RuntimeException e) {
        if (e.getCause() instanceof
            com.google.protobuf.InvalidProtocolBufferException) {
          throw (com.google.protobuf.InvalidProtocolBufferException)
              e.getCause();
        }
        throw e;
      }
    }
  };

  @java.lang.Override
  public com.google.protobuf.Parser<RowRange> getParserForType() {
    return PARSER;
  }

  public com.google.bigtable.v1.RowRange getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

