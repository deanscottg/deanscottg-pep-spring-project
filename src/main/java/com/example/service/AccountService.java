package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account){
        return accountRepository.save(account);
    }

    public Account findAccountById(Integer accountId){
        Optional <Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        return null;
    }

    public Boolean doesUserNameExist(String userName){
        return accountRepository.existsByUsername(userName);
    }


    
    
  
}
