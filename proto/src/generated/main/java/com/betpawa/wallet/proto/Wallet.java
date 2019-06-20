// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: wallet.proto

package com.betpawa.wallet.proto;

public final class Wallet {
  private Wallet() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BalanceResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BalanceResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_WalletRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_WalletRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014wallet.proto\032\033google/protobuf/empty.pr" +
      "oto\"\"\n\017BalanceResponse\022\017\n\007message\030\001 \001(\t\"" +
      "L\n\rWalletRequest\022\016\n\006userId\030\001 \001(\003\022\016\n\006amou" +
      "nt\030\002 \001(\t\022\033\n\010currency\030\003 \001(\0162\t.Currency*3\n" +
      "\tOperation\022\013\n\007DEPOSIT\020\000\022\014\n\010WITHDRAW\020\001\022\013\n" +
      "\007BALANCE\020\002*%\n\010Currency\022\007\n\003EUR\020\000\022\007\n\003USD\020\001" +
      "\022\007\n\003GBP\020\002*<\n\014ErrorMessage\022\026\n\022INSUFFICIEN" +
      "T_FUNDS\020\000\022\024\n\020UNKNOWN_CURRENCY\020\0012\251\001\n\rWall" +
      "etService\0223\n\007deposit\022\016.WalletRequest\032\026.g" +
      "oogle.protobuf.Empty\"\000\0224\n\010withdraw\022\016.Wal" +
      "letRequest\032\026.google.protobuf.Empty\"\000\022-\n\007" +
      "balance\022\016.WalletRequest\032\020.BalanceRespons" +
      "e\"\000B\034\n\030com.betpawa.wallet.protoP\001b\006proto" +
      "3"
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
          com.google.protobuf.EmptyProto.getDescriptor(),
        }, assigner);
    internal_static_BalanceResponse_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_BalanceResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BalanceResponse_descriptor,
        new java.lang.String[] { "Message", });
    internal_static_WalletRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_WalletRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_WalletRequest_descriptor,
        new java.lang.String[] { "UserId", "Amount", "Currency", });
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}