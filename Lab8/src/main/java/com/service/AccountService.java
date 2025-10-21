package com.service;

import com.dao.AccountDAO;
import com.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountDAO accountDAO;

    public Account findById(String username) {
        return accountDAO.findById(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
