package com.apiWallet.controllador;


import com.apiWallet.dto.ApiResponse;
import com.apiWallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallet/balance")
    public Mono<ResponseEntity<ApiResponse<Double>>> getBalance(@RequestParam String documento, @RequestParam String numeroCelular) {
        return walletService.getBalance(documento, numeroCelular)
                .map(wallet -> ResponseEntity.ok(new ApiResponse<>(wallet.getSaldo(), null)))
                .defaultIfEmpty(ResponseEntity.badRequest().body(new ApiResponse<>(null, "Datos no coinciden")));
    }
}
