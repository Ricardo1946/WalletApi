package com.apiWallet.service;

import com.apiWallet.repository.WalletRepository;
import com.apiWallet.model.Wallet;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Mono<Wallet> getBalance(String documento, String numeroCelular) {
        return walletRepository.findByDocumentoAndNumeroCelular(documento, numeroCelular)
                .collectList()
                .flatMap(wallets -> {
                    if (wallets.size() == 1) {
                        return Mono.just(wallets.get(0));
                    } else if (wallets.isEmpty()) {
                        return Mono.empty();
                    } else {
                        return Mono.error(new IncorrectResultSizeDataAccessException(1));
                    }
                });
    }
}
