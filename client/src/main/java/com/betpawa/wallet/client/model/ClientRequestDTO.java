package com.betpawa.wallet.client.model;

import com.betpawa.wallet.proto.Currency;
import lombok.Data;

@Data
public class ClientRequestDTO {
    private final Long userId;
    private final String amount;
    private final Currency currency;
}
