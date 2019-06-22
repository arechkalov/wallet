package com.betpawa.wallet.client.infrastructure.operations;

import com.betpawa.wallet.client.model.ClientRequestDTO;
import io.reactivex.Observable;

@FunctionalInterface
public interface Operation {
    Observable<String> execute(ClientRequestDTO requestDTO);
}
