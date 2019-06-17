package com.betpawa.wallet.client.infrastructure;

import com.betpawa.wallet.client.service.ClientService;
import com.betpawa.wallet.proto.WalletRequest;

public class Deposit implements Command {

    private final ClientService clientService;

    public Deposit(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute(WalletRequest walletRequest) {
        clientService.deposit(walletRequest);
    }
}
