package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Boolean existsByUsername(String username);
    Boolean existsByPassword(String password);
    Account findByUsernameAndPassword(String username, String password);
}
