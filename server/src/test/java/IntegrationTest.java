import com.betpawa.wallet.proto.BalanceResponse;
import com.betpawa.wallet.proto.Currency;
import com.betpawa.wallet.proto.WalletRequest;
import com.betpawa.wallet.proto.WalletServiceGrpc;
import com.betpawa.wallet.server.ServerApplication;
import com.betpawa.wallet.server.model.Balance;
import com.betpawa.wallet.server.repository.BalanceRepository;
import com.betpawa.wallet.server.service.WalletService;
import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ServerApplication.class})
@TestPropertySource(
    locations = "classpath:application-test.properties")
public class IntegrationTest {

    @Autowired
    private BalanceRepository balanceRepository;

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Test
    @Transactional
    void test() throws Exception {
        Balance b1 = Balance.builder()
            .balanceId(1L)
            .amount(BigDecimal.ZERO)
            .currency(Currency.USD)
            .userId(1L)
            .version(1)
            .build();

        Balance b2 = Balance.builder()
            .balanceId(2L)
            .amount(BigDecimal.ZERO)
            .currency(Currency.EUR)
            .userId(1L)
            .version(1)
            .build();

        balanceRepository.saveAndFlush(b1);
        balanceRepository.saveAndFlush(b2);
        balanceRepository.flush();

        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder
            .forName(serverName).directExecutor().addService(new WalletService(balanceRepository, validator)).build().start());

        WalletServiceGrpc.WalletServiceBlockingStub blockingStub = WalletServiceGrpc.newBlockingStub(
            grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
        Empty response = null;

        //Step 1
        try {
            response = blockingStub.withdraw(WalletRequest.newBuilder()
                .setUserId(1L)
                .setAmount("200")
                .setCurrency(Currency.USD)
                .build());
        } catch (StatusRuntimeException e) {
            assertThat(e.getMessage()).contains("Insufficient funds");
        }

        //Step 2
        response = blockingStub.deposit(WalletRequest.newBuilder()
            .setUserId(1L)
            .setAmount("100")
            .setCurrency(Currency.USD)
            .build());

        //Step 3
        BalanceResponse balance = blockingStub.balance(WalletRequest.newBuilder()
            .setUserId(1L)
            .build());
        assertThat(balance.getMessage()).isEqualTo("Balance(currency=USD, userId=1, amount=100)\n"
            + "Balance(currency=EUR, userId=1, amount=0)");

        //Step 4
        try {
            response = blockingStub.withdraw(WalletRequest.newBuilder()
                .setUserId(1L)
                .setAmount("200")
                .setCurrency(Currency.USD)
                .build());
        } catch (StatusRuntimeException e) {
            assertThat(e.getMessage()).contains("Insufficient funds");
        }

        //Step 5
        response = blockingStub.deposit(WalletRequest.newBuilder()
            .setUserId(1L)
            .setAmount("100")
            .setCurrency(Currency.EUR)
            .build());

        //Step 6
        BalanceResponse balance2 = blockingStub.balance(WalletRequest.newBuilder()
            .setUserId(1L)
            .build());

        assertThat(balance2.getMessage()).isEqualTo("Balance(currency=USD, userId=1, amount=100)\n"
            + "Balance(currency=EUR, userId=1, amount=100)");

        //Step 7
        try {
            response = blockingStub.withdraw(WalletRequest.newBuilder()
                .setUserId(1L)
                .setAmount("200")
                .setCurrency(Currency.USD)
                .build());
        } catch (StatusRuntimeException e) {
            assertThat(e.getMessage()).contains("Insufficient funds");
        }

        //Step 8
        response = blockingStub.deposit(WalletRequest.newBuilder()
            .setUserId(1L)
            .setAmount("100")
            .setCurrency(Currency.USD)
            .build());

        //Step 9
        BalanceResponse balance3 = blockingStub.balance(WalletRequest.newBuilder()
            .setUserId(1L)
            .build());

        assertThat(balance3.getMessage()).isEqualTo("Balance(currency=USD, userId=1, amount=200)\n"
            + "Balance(currency=EUR, userId=1, amount=100)");

        //Step 10
        blockingStub.withdraw(WalletRequest.newBuilder()
            .setUserId(1L)
            .setAmount("200")
            .setCurrency(Currency.USD)
            .build());

        //Step 11
        BalanceResponse balance4 = blockingStub.balance(WalletRequest.newBuilder()
            .setUserId(1L)
            .build());

        assertThat(balance4.getMessage()).isEqualTo("Balance(currency=USD, userId=1, amount=0)\n"
            + "Balance(currency=EUR, userId=1, amount=100)");

        //Step 12
        try {
            response = blockingStub.withdraw(WalletRequest.newBuilder()
                .setUserId(1L)
                .setAmount("200")
                .setCurrency(Currency.USD)
                .build());
        } catch (StatusRuntimeException e) {
            assertThat(e.getMessage()).contains("Insufficient funds");
        }
    }
}

