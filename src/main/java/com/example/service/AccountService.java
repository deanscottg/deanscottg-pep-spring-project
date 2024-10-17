package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

        // Create a new account
    public Account registerAccount(Account account){
        return accountRepository.save(account);
    }

        // Verify that account exists and login
    public Account loginAccount(Account account){
        Account verifiedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(verifiedAccount != null) return verifiedAccount;
        else return null;
    }

        // Retrieve an account given its id
    public Account findAccountById(Integer accountId){
        Optional <Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        return null;
    }

        // Check if a given username exists in the db previously
    public Boolean doesUserNameExist(String userName){
        return accountRepository.existsByUsername(userName);
    }

        // Check if a given password exists in the db previously
    public Boolean doesPasswordExist(String password){
        return accountRepository.existsByPassword(password);
    }
    
  
}
