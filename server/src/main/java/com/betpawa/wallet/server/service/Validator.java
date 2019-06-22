package com.betpawa.wallet.server.service;

import com.betpawa.wallet.proto.Currency;
import com.betpawa.wallet.proto.WalletRequest;
import com.betpawa.wallet.server.model.Balance;
import com.betpawa.wallet.server.service.exception.WalletException;
import io.grpc.Status;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public void validateNotNull(final Balance balance) {
        if (balance == null) {
            throw new WalletException(Status.NOT_FOUND, "Balance for this user doesn't exist");
        }
    }

    public void validateRequest(final WalletRequest request) {
        if (request.getCurrency().equals(Currency.UNRECOGNIZED)) {
            throw new WalletException(Status.INVALID_ARGUMENT, "Unknown currency");
        }
    }
}
