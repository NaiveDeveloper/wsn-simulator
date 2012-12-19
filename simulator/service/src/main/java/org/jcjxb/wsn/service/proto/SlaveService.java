// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SlaveService.proto

package org.jcjxb.wsn.service.proto;

public final class SlaveService {
  private SlaveService() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface InitSimCmdOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // repeated .org.jcjxb.wsn.service.SensorsOnHost sendorsOnHosts = 1;
    java.util.List<org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost> 
        getSendorsOnHostsList();
    org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost getSendorsOnHosts(int index);
    int getSendorsOnHostsCount();
    java.util.List<? extends org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder> 
        getSendorsOnHostsOrBuilderList();
    org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder getSendorsOnHostsOrBuilder(
        int index);
  }
  public static final class InitSimCmd extends
      com.google.protobuf.GeneratedMessage
      implements InitSimCmdOrBuilder {
    // Use InitSimCmd.newBuilder() to construct.
    private InitSimCmd(Builder builder) {
      super(builder);
    }
    private InitSimCmd(boolean noInit) {}
    
    private static final InitSimCmd defaultInstance;
    public static InitSimCmd getDefaultInstance() {
      return defaultInstance;
    }
    
    public InitSimCmd getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.jcjxb.wsn.service.proto.SlaveService.internal_static_org_jcjxb_wsn_service_InitSimCmd_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.jcjxb.wsn.service.proto.SlaveService.internal_static_org_jcjxb_wsn_service_InitSimCmd_fieldAccessorTable;
    }
    
    // repeated .org.jcjxb.wsn.service.SensorsOnHost sendorsOnHosts = 1;
    public static final int SENDORSONHOSTS_FIELD_NUMBER = 1;
    private java.util.List<org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost> sendorsOnHosts_;
    public java.util.List<org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost> getSendorsOnHostsList() {
      return sendorsOnHosts_;
    }
    public java.util.List<? extends org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder> 
        getSendorsOnHostsOrBuilderList() {
      return sendorsOnHosts_;
    }
    public int getSendorsOnHostsCount() {
      return sendorsOnHosts_.size();
    }
    public org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost getSendorsOnHosts(int index) {
      return sendorsOnHosts_.get(index);
    }
    public org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder getSendorsOnHostsOrBuilder(
        int index) {
      return sendorsOnHosts_.get(index);
    }
    
    private void initFields() {
      sendorsOnHosts_ = java.util.Collections.emptyList();
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
      for (int i = 0; i < sendorsOnHosts_.size(); i++) {
        output.writeMessage(1, sendorsOnHosts_.get(i));
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      for (int i = 0; i < sendorsOnHosts_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, sendorsOnHosts_.get(i));
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
    
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseDelimitedFrom(
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
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd prototype) {
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
       implements org.jcjxb.wsn.service.proto.SlaveService.InitSimCmdOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return org.jcjxb.wsn.service.proto.SlaveService.internal_static_org_jcjxb_wsn_service_InitSimCmd_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return org.jcjxb.wsn.service.proto.SlaveService.internal_static_org_jcjxb_wsn_service_InitSimCmd_fieldAccessorTable;
      }
      
      // Construct using org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getSendorsOnHostsFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        if (sendorsOnHostsBuilder_ == null) {
          sendorsOnHosts_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          sendorsOnHostsBuilder_.clear();
        }
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd.getDescriptor();
      }
      
      public org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd getDefaultInstanceForType() {
        return org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd.getDefaultInstance();
      }
      
      public org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd build() {
        org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd buildPartial() {
        org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd result = new org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd(this);
        int from_bitField0_ = bitField0_;
        if (sendorsOnHostsBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001)) {
            sendorsOnHosts_ = java.util.Collections.unmodifiableList(sendorsOnHosts_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.sendorsOnHosts_ = sendorsOnHosts_;
        } else {
          result.sendorsOnHosts_ = sendorsOnHostsBuilder_.build();
        }
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd) {
          return mergeFrom((org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd other) {
        if (other == org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd.getDefaultInstance()) return this;
        if (sendorsOnHostsBuilder_ == null) {
          if (!other.sendorsOnHosts_.isEmpty()) {
            if (sendorsOnHosts_.isEmpty()) {
              sendorsOnHosts_ = other.sendorsOnHosts_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureSendorsOnHostsIsMutable();
              sendorsOnHosts_.addAll(other.sendorsOnHosts_);
            }
            onChanged();
          }
        } else {
          if (!other.sendorsOnHosts_.isEmpty()) {
            if (sendorsOnHostsBuilder_.isEmpty()) {
              sendorsOnHostsBuilder_.dispose();
              sendorsOnHostsBuilder_ = null;
              sendorsOnHosts_ = other.sendorsOnHosts_;
              bitField0_ = (bitField0_ & ~0x00000001);
              sendorsOnHostsBuilder_ = 
                com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                   getSendorsOnHostsFieldBuilder() : null;
            } else {
              sendorsOnHostsBuilder_.addAllMessages(other.sendorsOnHosts_);
            }
          }
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
              org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder subBuilder = org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addSendorsOnHosts(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // repeated .org.jcjxb.wsn.service.SensorsOnHost sendorsOnHosts = 1;
      private java.util.List<org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost> sendorsOnHosts_ =
        java.util.Collections.emptyList();
      private void ensureSendorsOnHostsIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          sendorsOnHosts_ = new java.util.ArrayList<org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost>(sendorsOnHosts_);
          bitField0_ |= 0x00000001;
         }
      }
      
      private com.google.protobuf.RepeatedFieldBuilder<
          org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder> sendorsOnHostsBuilder_;
      
      public java.util.List<org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost> getSendorsOnHostsList() {
        if (sendorsOnHostsBuilder_ == null) {
          return java.util.Collections.unmodifiableList(sendorsOnHosts_);
        } else {
          return sendorsOnHostsBuilder_.getMessageList();
        }
      }
      public int getSendorsOnHostsCount() {
        if (sendorsOnHostsBuilder_ == null) {
          return sendorsOnHosts_.size();
        } else {
          return sendorsOnHostsBuilder_.getCount();
        }
      }
      public org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost getSendorsOnHosts(int index) {
        if (sendorsOnHostsBuilder_ == null) {
          return sendorsOnHosts_.get(index);
        } else {
          return sendorsOnHostsBuilder_.getMessage(index);
        }
      }
      public Builder setSendorsOnHosts(
          int index, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost value) {
        if (sendorsOnHostsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureSendorsOnHostsIsMutable();
          sendorsOnHosts_.set(index, value);
          onChanged();
        } else {
          sendorsOnHostsBuilder_.setMessage(index, value);
        }
        return this;
      }
      public Builder setSendorsOnHosts(
          int index, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder builderForValue) {
        if (sendorsOnHostsBuilder_ == null) {
          ensureSendorsOnHostsIsMutable();
          sendorsOnHosts_.set(index, builderForValue.build());
          onChanged();
        } else {
          sendorsOnHostsBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      public Builder addSendorsOnHosts(org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost value) {
        if (sendorsOnHostsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureSendorsOnHostsIsMutable();
          sendorsOnHosts_.add(value);
          onChanged();
        } else {
          sendorsOnHostsBuilder_.addMessage(value);
        }
        return this;
      }
      public Builder addSendorsOnHosts(
          int index, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost value) {
        if (sendorsOnHostsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureSendorsOnHostsIsMutable();
          sendorsOnHosts_.add(index, value);
          onChanged();
        } else {
          sendorsOnHostsBuilder_.addMessage(index, value);
        }
        return this;
      }
      public Builder addSendorsOnHosts(
          org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder builderForValue) {
        if (sendorsOnHostsBuilder_ == null) {
          ensureSendorsOnHostsIsMutable();
          sendorsOnHosts_.add(builderForValue.build());
          onChanged();
        } else {
          sendorsOnHostsBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      public Builder addSendorsOnHosts(
          int index, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder builderForValue) {
        if (sendorsOnHostsBuilder_ == null) {
          ensureSendorsOnHostsIsMutable();
          sendorsOnHosts_.add(index, builderForValue.build());
          onChanged();
        } else {
          sendorsOnHostsBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      public Builder addAllSendorsOnHosts(
          java.lang.Iterable<? extends org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost> values) {
        if (sendorsOnHostsBuilder_ == null) {
          ensureSendorsOnHostsIsMutable();
          super.addAll(values, sendorsOnHosts_);
          onChanged();
        } else {
          sendorsOnHostsBuilder_.addAllMessages(values);
        }
        return this;
      }
      public Builder clearSendorsOnHosts() {
        if (sendorsOnHostsBuilder_ == null) {
          sendorsOnHosts_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          sendorsOnHostsBuilder_.clear();
        }
        return this;
      }
      public Builder removeSendorsOnHosts(int index) {
        if (sendorsOnHostsBuilder_ == null) {
          ensureSendorsOnHostsIsMutable();
          sendorsOnHosts_.remove(index);
          onChanged();
        } else {
          sendorsOnHostsBuilder_.remove(index);
        }
        return this;
      }
      public org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder getSendorsOnHostsBuilder(
          int index) {
        return getSendorsOnHostsFieldBuilder().getBuilder(index);
      }
      public org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder getSendorsOnHostsOrBuilder(
          int index) {
        if (sendorsOnHostsBuilder_ == null) {
          return sendorsOnHosts_.get(index);  } else {
          return sendorsOnHostsBuilder_.getMessageOrBuilder(index);
        }
      }
      public java.util.List<? extends org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder> 
           getSendorsOnHostsOrBuilderList() {
        if (sendorsOnHostsBuilder_ != null) {
          return sendorsOnHostsBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(sendorsOnHosts_);
        }
      }
      public org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder addSendorsOnHostsBuilder() {
        return getSendorsOnHostsFieldBuilder().addBuilder(
            org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.getDefaultInstance());
      }
      public org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder addSendorsOnHostsBuilder(
          int index) {
        return getSendorsOnHostsFieldBuilder().addBuilder(
            index, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.getDefaultInstance());
      }
      public java.util.List<org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder> 
           getSendorsOnHostsBuilderList() {
        return getSendorsOnHostsFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilder<
          org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder> 
          getSendorsOnHostsFieldBuilder() {
        if (sendorsOnHostsBuilder_ == null) {
          sendorsOnHostsBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
              org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost.Builder, org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostOrBuilder>(
                  sendorsOnHosts_,
                  ((bitField0_ & 0x00000001) == 0x00000001),
                  getParentForChildren(),
                  isClean());
          sendorsOnHosts_ = null;
        }
        return sendorsOnHostsBuilder_;
      }
      
      // @@protoc_insertion_point(builder_scope:org.jcjxb.wsn.service.InitSimCmd)
    }
    
    static {
      defaultInstance = new InitSimCmd(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:org.jcjxb.wsn.service.InitSimCmd)
  }
  
  public static abstract class SService
      implements com.google.protobuf.Service {
    protected SService() {}
    
    public interface Interface {
    }
    
    public static com.google.protobuf.Service newReflectiveService(
        final Interface impl) {
      return new SService() {
      };
    }
    
    public static com.google.protobuf.BlockingService
        newReflectiveBlockingService(final BlockingInterface impl) {
      return new com.google.protobuf.BlockingService() {
        public final com.google.protobuf.Descriptors.ServiceDescriptor
            getDescriptorForType() {
          return getDescriptor();
        }
        
        public final com.google.protobuf.Message callBlockingMethod(
            com.google.protobuf.Descriptors.MethodDescriptor method,
            com.google.protobuf.RpcController controller,
            com.google.protobuf.Message request)
            throws com.google.protobuf.ServiceException {
          if (method.getService() != getDescriptor()) {
            throw new java.lang.IllegalArgumentException(
              "Service.callBlockingMethod() given method descriptor for " +
              "wrong service type.");
          }
          switch(method.getIndex()) {
            default:
              throw new java.lang.AssertionError("Can't get here.");
          }
        }
        
        public final com.google.protobuf.Message
            getRequestPrototype(
            com.google.protobuf.Descriptors.MethodDescriptor method) {
          if (method.getService() != getDescriptor()) {
            throw new java.lang.IllegalArgumentException(
              "Service.getRequestPrototype() given method " +
              "descriptor for wrong service type.");
          }
          switch(method.getIndex()) {
            default:
              throw new java.lang.AssertionError("Can't get here.");
          }
        }
        
        public final com.google.protobuf.Message
            getResponsePrototype(
            com.google.protobuf.Descriptors.MethodDescriptor method) {
          if (method.getService() != getDescriptor()) {
            throw new java.lang.IllegalArgumentException(
              "Service.getResponsePrototype() given method " +
              "descriptor for wrong service type.");
          }
          switch(method.getIndex()) {
            default:
              throw new java.lang.AssertionError("Can't get here.");
          }
        }
        
      };
    }
    
    public static final
        com.google.protobuf.Descriptors.ServiceDescriptor
        getDescriptor() {
      return org.jcjxb.wsn.service.proto.SlaveService.getDescriptor().getServices().get(0);
    }
    public final com.google.protobuf.Descriptors.ServiceDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    
    public final void callMethod(
        com.google.protobuf.Descriptors.MethodDescriptor method,
        com.google.protobuf.RpcController controller,
        com.google.protobuf.Message request,
        com.google.protobuf.RpcCallback<
          com.google.protobuf.Message> done) {
      if (method.getService() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "Service.callMethod() given method descriptor for wrong " +
          "service type.");
      }
      switch(method.getIndex()) {
        default:
          throw new java.lang.AssertionError("Can't get here.");
      }
    }
    
    public final com.google.protobuf.Message
        getRequestPrototype(
        com.google.protobuf.Descriptors.MethodDescriptor method) {
      if (method.getService() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "Service.getRequestPrototype() given method " +
          "descriptor for wrong service type.");
      }
      switch(method.getIndex()) {
        default:
          throw new java.lang.AssertionError("Can't get here.");
      }
    }
    
    public final com.google.protobuf.Message
        getResponsePrototype(
        com.google.protobuf.Descriptors.MethodDescriptor method) {
      if (method.getService() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "Service.getResponsePrototype() given method " +
          "descriptor for wrong service type.");
      }
      switch(method.getIndex()) {
        default:
          throw new java.lang.AssertionError("Can't get here.");
      }
    }
    
    public static Stub newStub(
        com.google.protobuf.RpcChannel channel) {
      return new Stub(channel);
    }
    
    public static final class Stub extends org.jcjxb.wsn.service.proto.SlaveService.SService implements Interface {
      private Stub(com.google.protobuf.RpcChannel channel) {
        this.channel = channel;
      }
      
      private final com.google.protobuf.RpcChannel channel;
      
      public com.google.protobuf.RpcChannel getChannel() {
        return channel;
      }
    }
    
    public static BlockingInterface newBlockingStub(
        com.google.protobuf.BlockingRpcChannel channel) {
      return new BlockingStub(channel);
    }
    
    public interface BlockingInterface {}
    
    private static final class BlockingStub implements BlockingInterface {
      private BlockingStub(com.google.protobuf.BlockingRpcChannel channel) {
        this.channel = channel;
      }
      
      private final com.google.protobuf.BlockingRpcChannel channel;
    }
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_org_jcjxb_wsn_service_InitSimCmd_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_org_jcjxb_wsn_service_InitSimCmd_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022SlaveService.proto\022\025org.jcjxb.wsn.serv" +
      "ice\032\023BasicDataType.proto\"J\n\nInitSimCmd\022<" +
      "\n\016sendorsOnHosts\030\001 \003(\0132$.org.jcjxb.wsn.s" +
      "ervice.SensorsOnHost2\n\n\010SServiceB.\n\033org." +
      "jcjxb.wsn.service.protoB\014SlaveService\210\001\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_org_jcjxb_wsn_service_InitSimCmd_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_org_jcjxb_wsn_service_InitSimCmd_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_org_jcjxb_wsn_service_InitSimCmd_descriptor,
              new java.lang.String[] { "SendorsOnHosts", },
              org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd.class,
              org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.jcjxb.wsn.service.proto.BasicDataType.getDescriptor(),
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
