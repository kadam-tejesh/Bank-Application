package com.example.BankApplication.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDTO {
    private long id;
    private String accountNo;
    private BigDecimal balance;
    public AccountResponseDTO() {
    }
}
