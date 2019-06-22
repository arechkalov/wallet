package com.betpawa.wallet.client.service;

import com.betpawa.wallet.client.model.ClientRequestDTO;
import com.betpawa.wallet.proto.BalanceResponse;
import com.betpawa.wallet.proto.WalletRequest;
import com.google.protobuf.Empty;
import io.reactivex.Observable;
import org.springframework.stereotype.Service;

import static com.betpawa.wallet.client.util.Util.toObservable;

/**
 * An RPC client that is used to talk to Wallet Server. The class is a thin wrapper on top of gRPC generated client.
 */
@Service
public class ClientService {

    private final GrpcGateway grpcGateway;

    public ClientService(GrpcGateway grpcGateway) {
        this.grpcGateway = grpcGateway;
    }

    public Observable<String> balance(ClientRequestDTO clientRequestDTO) {
        return toObservable(grpcGateway
            .balance(WalletRequest
                .newBuilder()
                .setUserId(clientRequestDTO.getUserId())
                .build()))
            .map(BalanceResponse::getMessage);
    }

    public Observable<String> deposit(ClientRequestDTO clientRequestDTO) {
        return toObservable(grpcGateway
            .deposit(WalletRequest
                .newBuilder()
                .setUserId(clientRequestDTO.getUserId())
                .setAmount(clientRequestDTO.getAmount())
                .setCurrency(clientRequestDTO.getCurrency())
                .build()))
            .map(Empty::toString);
    }

    public Observable<String> withdraw(ClientRequestDTO clientRequestDTO) {
        return toObservable(grpcGateway
            .withdraw(WalletRequest
                .newBuilder()
                .setUserId(clientRequestDTO.getUserId())
                .setAmount(clientRequestDTO.getAmount())
                .setCurrency(clientRequestDTO.getCurrency())
                .build()))
            .map(Empty::toString);
    }
}
