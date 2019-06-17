package com.betpawa.wallet.client.service;

import com.betpawa.wallet.proto.BalanceResponse;
import com.betpawa.wallet.proto.Currency;
import com.betpawa.wallet.proto.Empty;
import com.betpawa.wallet.proto.UserWalletsRequest;
import com.betpawa.wallet.proto.WalletRequest;
import com.betpawa.wallet.proto.WalletServiceGrpc;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientService {

    private final WalletServiceGrpc.WalletServiceFutureStub futureStub;

    public ClientService(WalletServiceGrpc.WalletServiceFutureStub futureStub) {
        this.futureStub = futureStub;
    }

    public ListenableFuture<BalanceResponse> getBalanceForUser(UserWalletsRequest userWalletsRequest) {
        return futureStub.balance(userWalletsRequest);
    }

    public ListenableFuture<Empty> deposit(WalletRequest walletRequest) {
        log.info("Deposit " + walletRequest.getAmount() + walletRequest.getCurrency() + " for userId = " + walletRequest.getUserId());
        return futureStub.deposit(walletRequest);
    }

    public ListenableFuture<Empty> withdraw(WalletRequest walletRequest) {
        log.info("Withdraw " + walletRequest.getAmount() + walletRequest.getCurrency() + " for userId = " + walletRequest.getUserId());
        return futureStub.withdraw(walletRequest);
    }
}
