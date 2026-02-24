package com.example.BankApplication.service;

import com.example.BankApplication.DTO.AccountResponseDTO;
import com.example.BankApplication.entity.Account;
import com.example.BankApplication.entity.Transactions;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.repository.AccountsRepo;
import com.example.BankApplication.repository.TransactionsRepo;
import com.example.BankApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AccountResponseDTO addAccount( long id,Account a){
        User user= userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        a.setUser(user);
        Account saved=accountsRepo.save(a);
        AccountResponseDTO response=new AccountResponseDTO();
        response.setId(saved.getId());
        response.setAccountNo(saved.getAccountNo());
        response.setBalance(saved.getBalance());
        return response;
    }
    public List<AccountResponseDTO> getAccounts(long id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
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
    public AccountResponseDTO deposit(double amount,String accountNo){
        if(amount<=0){
            throw new RuntimeException();
        }
        Account a=accountsRepo.findByAccountNo(accountNo).orElseThrow(()->new RuntimeException("account not found"));
        a.setBalance(a.getBalance()+amount);
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
    public AccountResponseDTO withdraw(double amount,String accountNo){
        if(amount<=0){
            throw new RuntimeException();
        }
        Account a=accountsRepo.findByAccountNo(accountNo).orElseThrow(()->new RuntimeException("account not found"));
        if(a.getBalance()>=amount) {
            a.setBalance(a.getBalance() - amount);
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
    public double getBalance(String accountNo){
        Account account=accountsRepo.findByAccountNo(accountNo).orElseThrow(()->new RuntimeException("account not found"));
        return account.getBalance();
    }
}
