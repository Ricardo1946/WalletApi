package com.apiWallet.controllador;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.apiWallet.dto.ApiResponse;
import com.apiWallet.model.Wallet;
import com.apiWallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSuccess() {
        Wallet wallet = new Wallet(1L, "1014236753", "3116758923", 100.00);
        when(walletService.getBalance(anyString(), anyString())).thenReturn(Mono.just(wallet));

        Mono<ResponseEntity<ApiResponse<Double>>> response = walletController.getBalance("1014236753", "3116758923");

        StepVerifier.create(response)
                .expectNextMatches(result -> {
                    boolean isStatusCodeCorrect = result.getStatusCode() == HttpStatus.OK;
                    boolean isDataCorrect = result.getBody() != null && result.getBody().getData().equals(100.00);
                    boolean isErrorMessageNull = result.getBody().getMensaje() == null;
                    return isStatusCodeCorrect && isDataCorrect && isErrorMessageNull;
                })
                .verifyComplete();
    }

    @Test
    public void testNotFound() {
        when(walletService.getBalance(anyString(), anyString())).thenReturn(Mono.empty());

        Mono<ResponseEntity<ApiResponse<Double>>> response = walletController.getBalance("9999999999", "9999999999");

        StepVerifier.create(response)
                .expectNextMatches(result -> {
                    boolean isStatusCodeCorrect = result.getStatusCode() == HttpStatus.BAD_REQUEST;
                    boolean isErrorMessageCorrect = result.getBody() != null && result.getBody().getMensaje().equals("Datos no coinciden");
                    boolean isDataNull = result.getBody().getData() == null;
                    return isStatusCodeCorrect && isErrorMessageCorrect && isDataNull;
                })
                .verifyComplete();
    }
}