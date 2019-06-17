package com.betpawa.wallet.server.repository;

import com.betpawa.wallet.server.model.Balance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.betpawa.wallet.proto.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BalanceRepositoryTest {

    @Autowired
    private BalanceRepository balanceRepository;

    @Test
    void shouldGetBalance() {
        Balance balance = balanceRepository.getBalanceByUserIdAndCurrency(1L, EUR);
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(200L));
        assertThat(balance.getCurrency()).isEqualTo(EUR);
    }

    @Test
    void shouldReturnAllBalancesForUser() {
        List<Balance> balances = balanceRepository.getBalanceByUserId(1L);
        assertThat(balances.size()).isEqualTo((2L));
    }

}
