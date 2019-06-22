package com.betpawa.wallet.server;

import com.betpawa.wallet.proto.Currency;
import com.betpawa.wallet.server.model.Balance;
import com.betpawa.wallet.server.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;

@SpringBootApplication
@EnableTransactionManagement
@EnableRetry
public class ServerApplication implements ApplicationRunner {

    @Value("${number.of.users}")
    private String users;

    @Autowired
    private BalanceRepository balanceRepository;

    public static void main(final String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        balanceRepository.deleteAll();
        for (long i = 1; i <= Long.valueOf(users); i++) {
            for (int j = 0; j < Currency.values().length - 1; j++) {
                balanceRepository.save(Balance.builder()
                    .amount(BigDecimal.ZERO)
                    .currency(Currency.forNumber(j))
                    .userId(i)
                    .build());
            }
        }
    }
}

