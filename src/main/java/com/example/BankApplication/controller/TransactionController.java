package com.example.BankApplication.controller;

import com.example.BankApplication.entity.Transactions;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.repository.AccountsRepo;
import com.example.BankApplication.repository.TransactionsRepo;
import com.example.BankApplication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping(path="doTransaction/{fromAccount}/{toAccount}/{amount}",consumes={"application/json"})
    public Transactions doTransaction(@PathVariable String fromAccount,@PathVariable String toAccount,@PathVariable double amount){

            return transactionService.doTransaction(fromAccount, toAccount, amount);


    }
    @GetMapping(path="TransactionHistory/between/{fromAccount}/{toAccount}",produces={"application/json"})
    public List<Transactions> getTransactions(@PathVariable String fromAccount,@PathVariable String toAccount) {

        return transactionService.transactionsHistory(fromAccount, toAccount);
    }

    @GetMapping(path="TransactionHistory/{accountNo}",produces={"application/json"})
    public List<Transactions> getTransactionsByAccountNo(@PathVariable String accountNo){

            return transactionService.transactionsHistory(accountNo);


    }
}
