package com.betpawa.wallet.client;

import com.betpawa.wallet.client.config.ClientProperties;
import com.betpawa.wallet.client.infrastructure.operations.OperationExecutor;
import com.betpawa.wallet.client.infrastructure.rounds.RoundResource;
import com.betpawa.wallet.client.service.ClientService;
import com.betpawa.wallet.client.service.RoundGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.apache.logging.log4j.util.Strings.LINE_SEPARATOR;

@SpringBootApplication
@Slf4j
public class ClientApplication implements ApplicationRunner {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OperationExecutor operationExecutor;

    @Autowired
    private ClientProperties clientProperties;

    @Autowired
    private RoundGenerator roundGenerator;

    public static void main(final String... args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        for (String name : args.getOptionNames()) {
            log.info("arg-" + name + "=" + args.getOptionValues(name));
        }
        Long users = clientProperties.getNumberOfUsers();
        Long requests = clientProperties.getNumberOfThreads();
        Long rounds = clientProperties.getNumberOfRounds();

        List<ForkJoinTask> taskList = new ArrayList<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(requests.intValue());
        long time = System.currentTimeMillis();
        for (int i = 1; i <= users; i++) {
            ForkJoinTask<?> submit =
                forkJoinPool
                    .submit(
                        () -> {
                            for (int j = 1; j <= rounds; j++) {
                                RoundResource roundResource = roundGenerator.generateRound();
                                List<String>
                                    balances =
                                    roundResource.start(clientService, operationExecutor, ThreadLocalRandom.current().nextLong(1L, users+1));
                                log.info(String.join(LINE_SEPARATOR, balances));
                            }
                        });

            taskList.add(submit);

        }

        taskList.forEach(ForkJoinTask::join);
        long timeTaken = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - time);
        int numberOFCalls = RoundResource.getNumberOfCalls().get();
        long perSecond = numberOFCalls/timeTaken;
        log.info("Number of requests per second results: {}", perSecond);
    }

}
