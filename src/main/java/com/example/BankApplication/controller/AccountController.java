package com.example.BankApplication.controller;


import com.example.BankApplication.DTO.AccountResponseDTO;
import com.example.BankApplication.entity.Account;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping(path="Account/user/{id}",consumes={"application/json"})
    public AccountResponseDTO addAccount(@PathVariable("id") long id, @RequestBody Account a){
       return accountService.addAccount(id,a);
    }
    @GetMapping(path="/Accounts")
    public List<AccountResponseDTO> getAccounts(){
        List<AccountResponseDTO> l=accountService.getAccounts();

        return l;
    }
    @PostMapping(path="deposit/{amount}/{accountNo}")
    public AccountResponseDTO deposit(@PathVariable BigDecimal amount,@PathVariable String accountNo){
        return accountService.deposit(amount,accountNo);
    }
    @PostMapping(path="withdraw/{amount}/{accountNo}")
    public AccountResponseDTO withdraw(@PathVariable BigDecimal amount,@PathVariable String accountNo){
        return accountService.withdraw(amount,accountNo);
    }
    @GetMapping(path="getBalance/{accountNo}")
    public BigDecimal getBalance(@PathVariable String accountNo){
        return accountService.getBalance(accountNo);
    }
}
