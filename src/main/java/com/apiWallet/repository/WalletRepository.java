package com.apiWallet.repository;

import com.apiWallet.model.Wallet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {
    Flux<Wallet> findByDocumentoAndNumeroCelular(String documento, String numeroCelular);
}
