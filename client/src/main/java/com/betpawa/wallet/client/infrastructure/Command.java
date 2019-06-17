package com.betpawa.wallet.client.infrastructure;

import com.betpawa.wallet.proto.WalletRequest;

@FunctionalInterface
public interface Command {
    void execute(WalletRequest walletRequest);
}
