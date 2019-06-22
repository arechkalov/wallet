package com.betpawa.wallet.client.service;

import com.betpawa.wallet.proto.BalanceResponse;
import com.betpawa.wallet.proto.WalletRequest;
import com.betpawa.wallet.proto.WalletServiceGrpc;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrpcGateway {

    private final WalletServiceGrpc.WalletServiceFutureStub futureStub;

    public GrpcGateway(WalletServiceGrpc.WalletServiceFutureStub futureStub) {
        this.futureStub = futureStub;
    }

    public ListenableFuture<BalanceResponse> balance(WalletRequest walletRequest) {
        log.debug("Get balance for userId = " + walletRequest.getUserId());
        return futureStub.balance(walletRequest);
    }

    public ListenableFuture<Empty> deposit(WalletRequest walletRequest) {
        log.debug("Deposit " + walletRequest.getAmount() + walletRequest.getCurrency() + " for userId = " + walletRequest.getUserId());
        return futureStub.deposit(walletRequest);
    }

    public ListenableFuture<Empty> withdraw(WalletRequest walletRequest) {
        log.debug("Withdraw " + walletRequest.getAmount() + walletRequest.getCurrency() + " for userId = " + walletRequest.getUserId());
        return futureStub.withdraw(walletRequest);
    }
}
