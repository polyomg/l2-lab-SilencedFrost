package com.dao;

import com.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDAO extends JpaRepository<Account, String>{
    void deleteByUsername(String username);
    Optional<Account> findByUsername(String username);
}
