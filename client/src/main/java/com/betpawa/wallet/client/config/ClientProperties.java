package com.betpawa.wallet.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "betpawa.client")
@PropertySource("classpath:client.properties")
@Getter
@Setter
public class ClientProperties {
    private Long numberOfUsers;
    private Long numberOfThreads;
    private Long numberOfRounds;
}
