package com.apiWallet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("wallet")
public class Wallet {
    @Id
    private Long id;
    private String documento;
    private String numeroCelular;
    private Double saldo;
}
