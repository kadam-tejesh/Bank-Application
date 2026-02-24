package com.example.BankApplication.service;

import com.example.BankApplication.entity.Account;
import com.example.BankApplication.entity.Transactions;
import com.example.BankApplication.repository.AccountsRepo;
import com.example.BankApplication.repository.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Transactions doTransaction(String fromAccountNo,String toAccountNo,double amount){
        if(fromAccountNo.equals(toAccountNo))
            throw new RuntimeException();
        Account fromAccount=repo.findByAccountNo(fromAccountNo).orElseThrow(()->new RuntimeException("Account not present"));
        Account toAccount=repo.findByAccountNo(toAccountNo).orElseThrow(()->new RuntimeException("account not present"));
        if(fromAccount.getBalance()<amount){
            throw new RuntimeException();
        }
        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);
        repo.save(fromAccount);
        repo.save(toAccount);
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
        return transactionsRepo.findByFromAccountAndToAccount(fromAccount, toAccount);
    }
    public List<Transactions> transactionsHistory(String accountNo){
        List<Transactions> sent= transactionsRepo.findByFromAccount(accountNo);
        List<Transactions> received=transactionsRepo.findByToAccount(accountNo);
        List<Transactions> all=new ArrayList<>();
        final boolean b = all.addAll(sent);
       final boolean a=all.addAll(received);
        return all;
    }


}
