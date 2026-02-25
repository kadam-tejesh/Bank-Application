package com.example.BankApplication.service;

import com.example.BankApplication.Authentication.EmailMatching;
import com.example.BankApplication.entity.Account;
import com.example.BankApplication.entity.Transactions;
import com.example.BankApplication.repository.AccountsRepo;
import com.example.BankApplication.repository.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionsRepo transactionsRepo;
    @Autowired
    private AccountsRepo repo;
    @Autowired
    private EmailMatching emailMatching;
    public Transactions doTransaction(String fromAccountNo, String toAccountNo, BigDecimal amount){
        if(fromAccountNo.equals(toAccountNo))
            throw new RuntimeException();
        String email= emailMatching.emailMatching();
        Account fromAccount=repo.findByAccountNo(fromAccountNo).orElseThrow(()->new RuntimeException("Account not present"));
        Account toAccount=repo.findByAccountNo(toAccountNo).orElseThrow(()->new RuntimeException("account not present"));
        if(!fromAccount.getUser().getEmail().equals(email)){
            throw new RuntimeException("credentials not matched");
        }
        if(fromAccount.getBalance().compareTo(amount)<0){
            throw new RuntimeException("not sufficient amount");
        }
        try {
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
            repo.save(fromAccount);
            repo.save(toAccount);
        }
        catch(Exception e){
            throw new RuntimeException("concurrency update detected");
        }
        Transactions transactions=new Transactions();
        transactions.setFromAccount(fromAccountNo);
        transactions.setToAccount(toAccountNo);
        transactions.setAmount(amount);
        transactions.setTime(new Date());
        transactions.setStatus("success");
        transactionsRepo.save(transactions);
        return transactions;
    }

    public List<Transactions> transactionsHistory(String fromAccount, String toAccount){
        String email= emailMatching.emailMatching();
        Account fromAcc=repo.findByAccountNo(fromAccount).orElseThrow(()->new RuntimeException("account not found"));
        if(!fromAcc.getUser().getEmail().equals(email)){
            throw new RuntimeException("user credentials doesn't match");
        }
        return transactionsRepo.findByFromAccountAndToAccount(fromAccount, toAccount);
    }
    public List<Transactions> transactionsHistory(String accountNo){

        String email= emailMatching.emailMatching();
        Account fromAcc=repo.findByAccountNo(accountNo).orElseThrow(()->new RuntimeException("account not found"));
        if(!fromAcc.getUser().getEmail().equals(email)){
            throw new RuntimeException("user credentials doesn't match");
        }
        List<Transactions> sent= transactionsRepo.findByFromAccount(accountNo);
        List<Transactions> received=transactionsRepo.findByToAccount(accountNo);
        List<Transactions> all=new ArrayList<>();
        all.addAll(sent);
       all.addAll(received);
        return all;
    }


}
