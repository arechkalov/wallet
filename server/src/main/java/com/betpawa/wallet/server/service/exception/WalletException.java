package com.betpawa.wallet.server.service.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class WalletException extends StatusRuntimeException {

    private final String message;

    public WalletException(Status status, String message) {
        super(status);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
