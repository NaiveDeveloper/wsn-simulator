// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rpc.proto

package org.jcjxb.wsn.rpc;

public final class LionRpcMessage {
  private LionRpcMessage() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface RequestOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required string service_name = 1;
    boolean hasServiceName();
    String getServiceName();
    
    // required string method_name = 2;
    boolean hasMethodName();
    String getMethodName();
    
    // required bytes request = 3;
    boolean hasRequest();
    com.google.protobuf.ByteString getRequest();
  }
  public static final class Request extends
      com.google.protobuf.GeneratedMessage
      implements RequestOrBuilder {
    // Use Request.newBuilder() to construct.
    private Request(Builder builder) {
      super(builder);
    }
    private Request(boolean noInit) {}
    
    private static final Request defaultInstance;
    public static Request getDefaultInstance() {
      return defaultInstance;
    }
    
    public Request getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.jcjxb.wsn.rpc.LionRpcMessage.internal_static_org_jcjxb_wsn_rpc_Request_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.jcjxb.wsn.rpc.LionRpcMessage.internal_static_org_jcjxb_wsn_rpc_Request_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required string service_name = 1;
    public static final int SERVICE_NAME_FIELD_NUMBER = 1;
    private java.lang.Object serviceName_;
    public boolean hasServiceName() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public String getServiceName() {
      java.lang.Object ref = serviceName_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          serviceName_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getServiceNameBytes() {
      java.lang.Object ref = serviceName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        serviceName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    // required string method_name = 2;
    public static final int METHOD_NAME_FIELD_NUMBER = 2;
    private java.lang.Object methodName_;
    public boolean hasMethodName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    public String getMethodName() {
      java.lang.Object ref = methodName_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          methodName_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getMethodNameBytes() {
      java.lang.Object ref = methodName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        methodName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    // required bytes request = 3;
    public static final int REQUEST_FIELD_NUMBER = 3;
    private com.google.protobuf.ByteString request_;
    public boolean hasRequest() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    public com.google.protobuf.ByteString getRequest() {
      return request_;
    }
    
    private void initFields() {
      serviceName_ = "";
      methodName_ = "";
      request_ = com.google.protobuf.ByteString.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasServiceName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasMethodName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasRequest()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getServiceNameBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getMethodNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeBytes(3, request_);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getServiceNameBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getMethodNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, request_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Request parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(org.jcjxb.wsn.rpc.LionRpcMessage.Request prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements org.jcjxb.wsn.rpc.LionRpcMessage.RequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return org.jcjxb.wsn.rpc.LionRpcMessage.internal_static_org_jcjxb_wsn_rpc_Request_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return org.jcjxb.wsn.rpc.LionRpcMessage.internal_static_org_jcjxb_wsn_rpc_Request_fieldAccessorTable;
      }
      
      // Construct using org.jcjxb.wsn.rpc.LionRpcMessage.Request.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        serviceName_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        methodName_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        request_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.jcjxb.wsn.rpc.LionRpcMessage.Request.getDescriptor();
      }
      
      public org.jcjxb.wsn.rpc.LionRpcMessage.Request getDefaultInstanceForType() {
        return org.jcjxb.wsn.rpc.LionRpcMessage.Request.getDefaultInstance();
      }
      
      public org.jcjxb.wsn.rpc.LionRpcMessage.Request build() {
        org.jcjxb.wsn.rpc.LionRpcMessage.Request result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private org.jcjxb.wsn.rpc.LionRpcMessage.Request buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        org.jcjxb.wsn.rpc.LionRpcMessage.Request result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public org.jcjxb.wsn.rpc.LionRpcMessage.Request buildPartial() {
        org.jcjxb.wsn.rpc.LionRpcMessage.Request result = new org.jcjxb.wsn.rpc.LionRpcMessage.Request(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.serviceName_ = serviceName_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.methodName_ = methodName_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.request_ = request_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.jcjxb.wsn.rpc.LionRpcMessage.Request) {
          return mergeFrom((org.jcjxb.wsn.rpc.LionRpcMessage.Request)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(org.jcjxb.wsn.rpc.LionRpcMessage.Request other) {
        if (other == org.jcjxb.wsn.rpc.LionRpcMessage.Request.getDefaultInstance()) return this;
        if (other.hasServiceName()) {
          setServiceName(other.getServiceName());
        }
        if (other.hasMethodName()) {
          setMethodName(other.getMethodName());
        }
        if (other.hasRequest()) {
          setRequest(other.getRequest());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasServiceName()) {
          
          return false;
        }
        if (!hasMethodName()) {
          
          return false;
        }
        if (!hasRequest()) {
          
          return false;
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              serviceName_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              methodName_ = input.readBytes();
              break;
            }
            case 26: {
              bitField0_ |= 0x00000004;
              request_ = input.readBytes();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required string service_name = 1;
      private java.lang.Object serviceName_ = "";
      public boolean hasServiceName() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public String getServiceName() {
        java.lang.Object ref = serviceName_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          serviceName_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setServiceName(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        serviceName_ = value;
        onChanged();
        return this;
      }
      public Builder clearServiceName() {
        bitField0_ = (bitField0_ & ~0x00000001);
        serviceName_ = getDefaultInstance().getServiceName();
        onChanged();
        return this;
      }
      void setServiceName(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000001;
        serviceName_ = value;
        onChanged();
      }
      
      // required string method_name = 2;
      private java.lang.Object methodName_ = "";
      public boolean hasMethodName() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      public String getMethodName() {
        java.lang.Object ref = methodName_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          methodName_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setMethodName(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        methodName_ = value;
        onChanged();
        return this;
      }
      public Builder clearMethodName() {
        bitField0_ = (bitField0_ & ~0x00000002);
        methodName_ = getDefaultInstance().getMethodName();
        onChanged();
        return this;
      }
      void setMethodName(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000002;
        methodName_ = value;
        onChanged();
      }
      
      // required bytes request = 3;
      private com.google.protobuf.ByteString request_ = com.google.protobuf.ByteString.EMPTY;
      public boolean hasRequest() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      public com.google.protobuf.ByteString getRequest() {
        return request_;
      }
      public Builder setRequest(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        request_ = value;
        onChanged();
        return this;
      }
      public Builder clearRequest() {
        bitField0_ = (bitField0_ & ~0x00000004);
        request_ = getDefaultInstance().getRequest();
        onChanged();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:org.jcjxb.wsn.rpc.Request)
    }
    
    static {
      defaultInstance = new Request(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:org.jcjxb.wsn.rpc.Request)
  }
  
  public interface ResponseOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // optional bytes response = 1;
    boolean hasResponse();
    com.google.protobuf.ByteString getResponse();
    
    // optional string errorText = 2;
    boolean hasErrorText();
    String getErrorText();
  }
  public static final class Response extends
      com.google.protobuf.GeneratedMessage
      implements ResponseOrBuilder {
    // Use Response.newBuilder() to construct.
    private Response(Builder builder) {
      super(builder);
    }
    private Response(boolean noInit) {}
    
    private static final Response defaultInstance;
    public static Response getDefaultInstance() {
      return defaultInstance;
    }
    
    public Response getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.jcjxb.wsn.rpc.LionRpcMessage.internal_static_org_jcjxb_wsn_rpc_Response_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.jcjxb.wsn.rpc.LionRpcMessage.internal_static_org_jcjxb_wsn_rpc_Response_fieldAccessorTable;
    }
    
    private int bitField0_;
    // optional bytes response = 1;
    public static final int RESPONSE_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString response_;
    public boolean hasResponse() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public com.google.protobuf.ByteString getResponse() {
      return response_;
    }
    
    // optional string errorText = 2;
    public static final int ERRORTEXT_FIELD_NUMBER = 2;
    private java.lang.Object errorText_;
    public boolean hasErrorText() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    public String getErrorText() {
      java.lang.Object ref = errorText_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          errorText_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getErrorTextBytes() {
      java.lang.Object ref = errorText_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        errorText_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    private void initFields() {
      response_ = com.google.protobuf.ByteString.EMPTY;
      errorText_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, response_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getErrorTextBytes());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, response_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getErrorTextBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.jcjxb.wsn.rpc.LionRpcMessage.Response parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(org.jcjxb.wsn.rpc.LionRpcMessage.Response prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements org.jcjxb.wsn.rpc.LionRpcMessage.ResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return org.jcjxb.wsn.rpc.LionRpcMessage.internal_static_org_jcjxb_wsn_rpc_Response_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return org.jcjxb.wsn.rpc.LionRpcMessage.internal_static_org_jcjxb_wsn_rpc_Response_fieldAccessorTable;
      }
      
      // Construct using org.jcjxb.wsn.rpc.LionRpcMessage.Response.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        response_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        errorText_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.jcjxb.wsn.rpc.LionRpcMessage.Response.getDescriptor();
      }
      
      public org.jcjxb.wsn.rpc.LionRpcMessage.Response getDefaultInstanceForType() {
        return org.jcjxb.wsn.rpc.LionRpcMessage.Response.getDefaultInstance();
      }
      
      public org.jcjxb.wsn.rpc.LionRpcMessage.Response build() {
        org.jcjxb.wsn.rpc.LionRpcMessage.Response result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private org.jcjxb.wsn.rpc.LionRpcMessage.Response buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        org.jcjxb.wsn.rpc.LionRpcMessage.Response result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public org.jcjxb.wsn.rpc.LionRpcMessage.Response buildPartial() {
        org.jcjxb.wsn.rpc.LionRpcMessage.Response result = new org.jcjxb.wsn.rpc.LionRpcMessage.Response(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.response_ = response_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.errorText_ = errorText_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.jcjxb.wsn.rpc.LionRpcMessage.Response) {
          return mergeFrom((org.jcjxb.wsn.rpc.LionRpcMessage.Response)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(org.jcjxb.wsn.rpc.LionRpcMessage.Response other) {
        if (other == org.jcjxb.wsn.rpc.LionRpcMessage.Response.getDefaultInstance()) return this;
        if (other.hasResponse()) {
          setResponse(other.getResponse());
        }
        if (other.hasErrorText()) {
          setErrorText(other.getErrorText());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              response_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              errorText_ = input.readBytes();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // optional bytes response = 1;
      private com.google.protobuf.ByteString response_ = com.google.protobuf.ByteString.EMPTY;
      public boolean hasResponse() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public com.google.protobuf.ByteString getResponse() {
        return response_;
      }
      public Builder setResponse(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        response_ = value;
        onChanged();
        return this;
      }
      public Builder clearResponse() {
        bitField0_ = (bitField0_ & ~0x00000001);
        response_ = getDefaultInstance().getResponse();
        onChanged();
        return this;
      }
      
      // optional string errorText = 2;
      private java.lang.Object errorText_ = "";
      public boolean hasErrorText() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      public String getErrorText() {
        java.lang.Object ref = errorText_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          errorText_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setErrorText(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        errorText_ = value;
        onChanged();
        return this;
      }
      public Builder clearErrorText() {
        bitField0_ = (bitField0_ & ~0x00000002);
        errorText_ = getDefaultInstance().getErrorText();
        onChanged();
        return this;
      }
      void setErrorText(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000002;
        errorText_ = value;
        onChanged();
      }
      
      // @@protoc_insertion_point(builder_scope:org.jcjxb.wsn.rpc.Response)
    }
    
    static {
      defaultInstance = new Response(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:org.jcjxb.wsn.rpc.Response)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_org_jcjxb_wsn_rpc_Request_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_org_jcjxb_wsn_rpc_Request_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_org_jcjxb_wsn_rpc_Response_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_org_jcjxb_wsn_rpc_Response_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\trpc.proto\022\021org.jcjxb.wsn.rpc\"E\n\007Reques" +
      "t\022\024\n\014service_name\030\001 \002(\t\022\023\n\013method_name\030\002" +
      " \002(\t\022\017\n\007request\030\003 \002(\014\"/\n\010Response\022\020\n\010res" +
      "ponse\030\001 \001(\014\022\021\n\terrorText\030\002 \001(\tB#\n\021org.jc" +
      "jxb.wsn.rpcB\016LionRpcMessage"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_org_jcjxb_wsn_rpc_Request_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_org_jcjxb_wsn_rpc_Request_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_org_jcjxb_wsn_rpc_Request_descriptor,
              new java.lang.String[] { "ServiceName", "MethodName", "Request", },
              org.jcjxb.wsn.rpc.LionRpcMessage.Request.class,
              org.jcjxb.wsn.rpc.LionRpcMessage.Request.Builder.class);
          internal_static_org_jcjxb_wsn_rpc_Response_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_org_jcjxb_wsn_rpc_Response_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_org_jcjxb_wsn_rpc_Response_descriptor,
              new java.lang.String[] { "Response", "ErrorText", },
              org.jcjxb.wsn.rpc.LionRpcMessage.Response.class,
              org.jcjxb.wsn.rpc.LionRpcMessage.Response.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
