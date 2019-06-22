package com.betpawa.wallet.client.infrastructure.operations;

import com.betpawa.wallet.client.model.ClientRequestDTO;
import com.betpawa.wallet.client.service.ClientService;
import io.reactivex.Observable;

public class WithdrawOperation implements Operation {

    private final ClientService clientService;

    public WithdrawOperation(ClientService clientService) {
        this.clientService = clientService;
    }


    @Override
    public Observable<String> execute(ClientRequestDTO requestDTO) {
        return clientService.withdraw(requestDTO);
    }
}
