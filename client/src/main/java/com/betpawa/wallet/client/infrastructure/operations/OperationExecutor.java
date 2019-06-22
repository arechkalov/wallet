package com.betpawa.wallet.client.infrastructure.operations;

import com.betpawa.wallet.client.model.ClientRequestDTO;
import io.reactivex.Observable;
import org.springframework.stereotype.Service;

@Service
public class OperationExecutor {

    public Observable<String> executeOperation(final Operation operation, final ClientRequestDTO requestDTO) {
        return operation.execute(requestDTO);
    }
}
