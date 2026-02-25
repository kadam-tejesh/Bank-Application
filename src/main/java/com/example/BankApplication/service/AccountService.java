package com.example.BankApplication.service;

import com.example.BankApplication.Authentication.EmailMatching;
import com.example.BankApplication.DTO.AccountResponseDTO;
import com.example.BankApplication.entity.Account;
import com.example.BankApplication.entity.Transactions;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.repository.AccountsRepo;
import com.example.BankApplication.repository.TransactionsRepo;
import com.example.BankApplication.repository.UserRepository;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountService {
    @Autowired
     private AccountsRepo accountsRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionsRepo repo;
    @Autowired
    private EmailMatching emailMatching;
    public AccountResponseDTO addAccount( long id,Account a){
        User user= userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        String email= emailMatching.emailMatching();
        if(!user.getEmail().equals(email)){
            throw new RuntimeException("user credentials does not match");
        }
        a.setUser(user);
        Account saved=accountsRepo.save(a);
        AccountResponseDTO response=new AccountResponseDTO();
        response.setId(saved.getId());
        response.setAccountNo(saved.getAccountNo());
        response.setBalance(saved.getBalance());
        return response;
    }
    public List<AccountResponseDTO> getAccounts(){
        String email= emailMatching.emailMatching();
        User user=userRepository.findByEmail(email);
        List<Account> l;

            l = user.getAccounts();


            List<AccountResponseDTO> response = new ArrayList<>();
            for (Account a : l) {
                AccountResponseDTO r = new AccountResponseDTO();
                r.setId(a.getId());
                r.setAccountNo(a.getAccountNo());
                r.setBalance(a.getBalance());
                response.add(r);
            }

        return response;
    }
    public AccountResponseDTO deposit(BigDecimal amount, String accountNo){


        String email= emailMatching.emailMatching();

        Account a=accountsRepo.findByAccountNo(accountNo).orElseThrow(()->new RuntimeException("account not found"));
        if(!a.getUser().getEmail().equals(email)){
            throw new RuntimeException("user credentials doesn't match");
        }
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("amount must be greater than zero");
        }
        a.setBalance(a.getBalance().add(amount));
        accountsRepo.save(a);
        Transactions t=new Transactions();
        t.setToAccount(accountNo);
        t.setFromAccount("self");
        t.setAmount(amount);
        t.setTime(new Date());
        t.setStatus("deposit successful");


        repo.save(t);
        AccountResponseDTO responseDTO=new AccountResponseDTO();
        responseDTO.setBalance(a.getBalance());
        responseDTO.setAccountNo(a.getAccountNo());
        responseDTO.setId(a.getId());
        return responseDTO;
    }
    public AccountResponseDTO withdraw(BigDecimal amount,String accountNo){
        String email= emailMatching.emailMatching();
        Account a=accountsRepo.findByAccountNo(accountNo).orElseThrow(()->new RuntimeException("account not found"));
        if(!a.getUser().getEmail().equals(email)){
            throw new RuntimeException("user credentials doesn't match");
        }
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("amount must be greater than zero");
        }
        if(a.getBalance().compareTo(amount)>=0) {
            a.setBalance(a.getBalance().subtract(amount));
            accountsRepo.save(a);

            Transactions t = new Transactions();
            t.setToAccount("self");
            t.setFromAccount(a.getAccountNo());
            t.setAmount(amount);
            t.setTime(new Date());
            t.setStatus("withdrawal successful");
            repo.save(t);
        }
        AccountResponseDTO responseDTO=new AccountResponseDTO();
        responseDTO.setBalance(a.getBalance());
        responseDTO.setAccountNo(a.getAccountNo());
        responseDTO.setId(a.getId());
        return responseDTO;
    }
    public BigDecimal getBalance(String accountNo){
        String email= emailMatching.emailMatching();
        Account account=accountsRepo.findByAccountNo(accountNo).orElseThrow(()->new RuntimeException("account not found"));
        if(!account.getUser().getEmail().equals(email)){
            throw new RuntimeException("user credentials doesn't match");
        }
        return account.getBalance();
    }
}
