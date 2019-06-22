package com.betpawa.wallet.server.repository;

import com.betpawa.wallet.proto.Currency;
import com.betpawa.wallet.server.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Transactional(readOnly = true)
    Balance getBalanceByUserIdAndCurrency(Long userId, Currency currency);

    @Transactional(readOnly = true)
    List<Balance> getBalanceByUserId(Long userId);
}
