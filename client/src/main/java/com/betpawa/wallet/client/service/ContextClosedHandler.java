package com.betpawa.wallet.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ContextClosedHandler implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        executor.shutdown();
    }
}
