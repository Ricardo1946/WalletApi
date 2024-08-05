package com.apiWallet.service;

import com.apiWallet.model.Wallet;
import com.apiWallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBalance_Success() {
        Wallet wallet = new Wallet(1L, "1014236753", "3116758923", 100.00);
        when(walletRepository.findByDocumentoAndNumeroCelular(anyString(), anyString()))
                .thenReturn(Flux.just(wallet));

        Mono<Wallet> result = walletService.getBalance("1014236753", "3116758923");

        StepVerifier.create(result)
                .expectNext(wallet)
                .verifyComplete();
    }

    @Test
    public void testGetBalance_NotFound() {
        when(walletRepository.findByDocumentoAndNumeroCelular(anyString(), anyString()))
                .thenReturn(Flux.empty());

        Mono<Wallet> result = walletService.getBalance("9999999999", "9999999999");

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetBalance_MultipleResults() {
        Wallet wallet1 = new Wallet(1L, "1014236753", "3116758923", 100.00);
        Wallet wallet2 = new Wallet(2L, "1014236753", "3116758923", 200.00);

        when(walletRepository.findByDocumentoAndNumeroCelular(anyString(), anyString()))
                .thenReturn(Mono.just(wallet1).concatWith(Mono.just(wallet2)));

        Mono<Wallet> result = walletService.getBalance("1014236753", "3116758923");

        StepVerifier.create(result)
                .expectError(IncorrectResultSizeDataAccessException.class)
                .verify();
    }
}