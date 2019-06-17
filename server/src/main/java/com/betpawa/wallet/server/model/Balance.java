package com.betpawa.wallet.server.model;


import com.betpawa.wallet.proto.Currency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Builder
@ToString(exclude = {"balanceId", "version"})
@Getter
@Entity
@Table(name = "BALANCE")
public class Balance implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "BALANCE_ID", unique = true, nullable = false)
    private Long balanceId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "CURRENCY_ID", nullable = false, unique = true)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Version
    @Column(name="OPT_LOCK")
    private int version;

    public void deposit(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }

}
