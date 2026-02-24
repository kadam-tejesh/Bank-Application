package com.example.BankApplication.DTO;

import lombok.Data;

@Data
public class AccountResponseDTO {
    private long id;
    private String accountNo;
    private double balance;
    public AccountResponseDTO() {
    }
}
