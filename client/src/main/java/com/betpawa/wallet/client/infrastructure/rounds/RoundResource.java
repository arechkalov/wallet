package com.betpawa.wallet.client.infrastructure.rounds;

import com.betpawa.wallet.client.infrastructure.operations.BalanceOperation;
import com.betpawa.wallet.client.infrastructure.operations.DepositOperation;
import com.betpawa.wallet.client.infrastructure.operations.OperationExecutor;
import com.betpawa.wallet.client.infrastructure.operations.WithdrawOperation;
import com.betpawa.wallet.client.model.ClientRequestDTO;
import com.betpawa.wallet.client.service.ClientService;
import com.betpawa.wallet.proto.Currency;
import io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("Duplicates")
@Slf4j
public enum RoundResource {

    ROUNDA {
        @Override
        public List<String> start(ClientService clientService, OperationExecutor operationExecutor, Long userId) {
            log.debug("Starting round A");
            List<String> strings = new ArrayList<>(16);
            Observable.just(
                operationExecutor.executeOperation(new DepositOperation(clientService), new ClientRequestDTO(userId, "100", Currency.USD)),
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "200", Currency.USD)),
                operationExecutor.executeOperation(new DepositOperation(clientService), new ClientRequestDTO(userId, "100", Currency.EUR)),
                operationExecutor.executeOperation(new BalanceOperation(clientService), new ClientRequestDTO(userId, null, null)),
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "100", Currency.USD)),
                operationExecutor.executeOperation(new BalanceOperation(clientService), new ClientRequestDTO(userId, null, null)),
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "100", Currency.USD)))
                .blockingSubscribe(
                    o -> o.subscribe((s) -> {
                            strings.add(s);
                            getNumberOfCalls().incrementAndGet();
                        },
                        e -> {
                            log.error(e.getMessage());
                            getNumberOfCalls().incrementAndGet();
                            getNumberOfFailures().incrementAndGet();
                        }
                    ),
                    e -> log.error(e.getMessage()),
                    () -> log.debug("round A done"));
            return strings;
        }
    },

    ROUNDB {
        @Override
        public List<String> start(ClientService clientService, OperationExecutor operationExecutor, Long userId) {
            log.debug("Starting round B");
            List<String> strings = new ArrayList<>(16);
            Observable.just(
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "100", Currency.GBP)),
                operationExecutor.executeOperation(new DepositOperation(clientService), new ClientRequestDTO(userId, "300", Currency.GBP)),
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "100", Currency.GBP)),
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "100", Currency.GBP)),
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "100", Currency.GBP)))
                .blockingSubscribe(
                    o -> o.subscribe((s) -> {
                            strings.add(s);
                            getNumberOfCalls().incrementAndGet();
                        },
                        e -> {
                            log.error(e.getMessage());
                            getNumberOfCalls().incrementAndGet();
                            getNumberOfFailures().incrementAndGet();
                        }
                    ),
                    e -> log.error(e.getMessage()),
                    () -> log.debug("round B done"));
            return strings;
        }
    },

    ROUNDC {
        @Override
        public List<String> start(ClientService clientService, OperationExecutor operationExecutor, Long userId) {
            log.debug("Starting round C");
            List<String> strings = new ArrayList<>(16);
            Observable.just(
                operationExecutor.executeOperation(new BalanceOperation(clientService), new ClientRequestDTO(userId, null, null)),
                operationExecutor.executeOperation(new DepositOperation(clientService), new ClientRequestDTO(userId, "100", Currency.USD)),
                operationExecutor.executeOperation(new DepositOperation(clientService), new ClientRequestDTO(userId, "100", Currency.USD)),
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "100", Currency.USD)),
                operationExecutor.executeOperation(new DepositOperation(clientService), new ClientRequestDTO(userId, "100", Currency.USD)),
                operationExecutor.executeOperation(new BalanceOperation(clientService), new ClientRequestDTO(userId, null, null)),
                operationExecutor.executeOperation(new WithdrawOperation(clientService), new ClientRequestDTO(userId, "200", Currency.USD)),
                operationExecutor.executeOperation(new BalanceOperation(clientService), new ClientRequestDTO(userId, null, null)))
                .blockingSubscribe(
                    o -> o.subscribe((s) -> {
                            strings.add(s);
                            getNumberOfCalls().incrementAndGet();
                        },
                        e -> {
                            log.error(e.getMessage());
                            getNumberOfCalls().incrementAndGet();
                            getNumberOfFailures().incrementAndGet();
                        }
                    ),
                    e -> log.error(e.getMessage()),
                    () -> log.debug("round C done"));
            return strings;
        }
    };

    public abstract List<String> start(ClientService clientService, OperationExecutor operationExecutor, Long userId);

    private static final List<RoundResource> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final AtomicInteger numberOfCalls = new AtomicInteger();
    private static final AtomicInteger numberOfFailures = new AtomicInteger();

    public static RoundResource randomRound() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static AtomicInteger getNumberOfCalls() {
        return numberOfCalls;
    }

    public static AtomicInteger getNumberOfFailures() {
        return numberOfFailures;
    }
}
