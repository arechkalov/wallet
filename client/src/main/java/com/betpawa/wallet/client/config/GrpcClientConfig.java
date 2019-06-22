package com.betpawa.wallet.client.config;

import com.betpawa.wallet.proto.WalletServiceGrpc;
import io.grpc.Channel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GrpcClientConfig {

	@GrpcClient("wallet-server")
	private Channel serverChannel;

	@Bean
	public GrpcChannelConfigurer keepAliveClientConfigurer() {
		return (channelBuilder, name) -> {
			if (channelBuilder instanceof NettyChannelBuilder) {
				((NettyChannelBuilder) channelBuilder)
					.keepAliveTime(10, TimeUnit.SECONDS)
					.keepAliveTimeout(5, TimeUnit.SECONDS)
					.usePlaintext();
			}
		};
	}


	@Bean
	public WalletServiceGrpc.WalletServiceFutureStub walletServiceFutureStub() {
		return WalletServiceGrpc.newFutureStub(serverChannel);
	}
}
