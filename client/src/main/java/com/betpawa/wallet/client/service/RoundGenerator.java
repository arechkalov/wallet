package com.betpawa.wallet.client.service;

import com.betpawa.wallet.client.infrastructure.rounds.RoundResource;
import org.springframework.stereotype.Service;

@Service
public class RoundGenerator {
    public RoundResource generateRound() {
        return RoundResource.randomRound();
    }
}
