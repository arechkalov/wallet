package com.betpawa.wallet.server.service;

import com.betpawa.wallet.proto.BalanceResponse;
import com.betpawa.wallet.proto.Currency;
import com.betpawa.wallet.proto.WalletRequest;
import com.betpawa.wallet.proto.WalletServiceGrpc;
import com.betpawa.wallet.server.model.Balance;
import com.betpawa.wallet.server.repository.BalanceRepository;
import com.betpawa.wallet.server.service.exception.WalletException;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@Slf4j
public class WalletService extends WalletServiceGrpc.WalletServiceImplBase {

    private final BalanceRepository balanceRepository;

    public WalletService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }


    @Override
    @Transactional
    public void deposit(WalletRequest request, StreamObserver<Empty> responseObserver) {
        log.info("Received request deposit " + request.getAmount() + request.getCurrency() + " for userId = " + request.getUserId());
        validateCurrency(request);

        try {
            final BigDecimal amount = new BigDecimal(request.getAmount());
            Balance balance = balanceRepository.getBalanceByUserIdAndCurrency(request.getUserId(), request.getCurrency());
            validateNotNull(balance);
            balance.deposit(amount);
            balanceRepository.saveAndFlush(balance);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("An error occurred: " + e.getMessage());
            responseObserver.onError(
                Status.INTERNAL
                    .withDescription(e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        } finally {
            balanceRepository.flush();
        }
    }

    @Override
    @Transactional
    public void withdraw(WalletRequest request, StreamObserver<Empty> responseObserver) {
        log.info("Received request withdraw " + request.getAmount() + request.getCurrency() + " for userId = " + request.getUserId());
        validateCurrency(request);
        try {
            final BigDecimal amount = new BigDecimal(request.getAmount());
            Balance balance = balanceRepository.getBalanceByUserIdAndCurrency(request.getUserId(), request.getCurrency());
            validateNotNull(balance);

            if (balance.getAmount().compareTo(amount) < 0) {
                throw new WalletException(Status.FAILED_PRECONDITION, "Insufficient funds");
            }
            balance.withdraw(amount);
            balanceRepository.saveAndFlush(balance);
            log.info("Withdrawn: " + amount + request.getCurrency() + " for userId = " + balance.getUserId());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (WalletException e) {
            log.error(e.getMessage());
            responseObserver.onError(new StatusRuntimeException(e.getStatus().withDescription(e.getMessage())));
        } catch (Exception e) {
            log.error(e.getMessage());
            responseObserver.onError(
                Status.INTERNAL
                    .withDescription(e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        } finally {
            balanceRepository.flush();
        }
    }

    public void balance(WalletRequest request, StreamObserver<BalanceResponse> responseObserver) {
        try {
            List<Balance> balances = balanceRepository.getBalanceByUserId(request.getUserId());
            String message = balances.stream()
                .map(Balance::toString)
                .collect(Collectors.joining("\n"));
            responseObserver.onNext(BalanceResponse.newBuilder().setMessage(message).build());
            responseObserver.onCompleted();
        } catch (WalletException e) {
            log.error(e.getMessage());
            responseObserver.onError(new StatusRuntimeException(e.getStatus().withDescription(e.getMessage())));
        } catch (Exception e) {
            responseObserver.onError(
                Status.INTERNAL
                    .withDescription(e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        } finally {
            balanceRepository.flush();
        }
    }

    private static void validateNotNull(Balance balance) {
        if (balance == null) {
            throw new WalletException(Status.NOT_FOUND, "Balance for this user doesn't exist");
        }
    }

    private static void validateCurrency(WalletRequest request) {
        if (request.getCurrency().equals(Currency.UNRECOGNIZED)) {
            throw new WalletException(Status.INVALID_ARGUMENT, "Unknown currency");
        }
    }
}
