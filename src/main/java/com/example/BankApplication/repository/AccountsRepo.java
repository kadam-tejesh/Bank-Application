package com.example.BankApplication.repository;

import com.example.BankApplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepo extends JpaRepository<Account,Long> {
    Optional<Account> findByAccountNo(String accountNo);
}
