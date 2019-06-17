package com.betpawa.wallet.client;

import com.betpawa.wallet.client.model.ClientProperties;
import com.betpawa.wallet.client.service.ClientService;
import com.betpawa.wallet.proto.BalanceResponse;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ClientApplication implements ApplicationRunner{

    private static final Logger logger = LoggerFactory.getLogger(ClientApplication.class);

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }

    @Autowired
    private ClientService clientService;

    public static void main(final String... args) {
        SpringApplication.exit(SpringApplication.run(ClientApplication.class, args));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        for (String name : args.getOptionNames()) {
            logger.info("arg-" + name + "=" + args.getOptionValues(name));
        }

//        ListenableFuture<BalanceResponse> balanceForUser = clientService.getBalanceForUser(1L);
//        System.out.println("user balance: " + balanceForUser.get().getMessage());


    }
}
