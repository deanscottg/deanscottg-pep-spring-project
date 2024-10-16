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

    public Account registerAccount(Account account){
        return accountRepository.save(account);
    }
    public Account loginAccount(Account account){
        Account verifiedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(verifiedAccount != null) return verifiedAccount;
        else return null;
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

    public Boolean doesPasswordExist(String password){
        return accountRepository.existsByPassword(password);
    }
    
  
}
