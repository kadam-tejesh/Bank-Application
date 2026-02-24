package com.example.BankApplication.repository;

import com.example.BankApplication.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface TransactionsRepo extends JpaRepository<Transactions,Long> {
    List<Transactions> findByFromAccountAndToAccount(String fromAccount, String toAccount);
    List<Transactions> findByFromAccount(String fromAccount);
    List<Transactions> findByToAccount(String toAccount);
}
