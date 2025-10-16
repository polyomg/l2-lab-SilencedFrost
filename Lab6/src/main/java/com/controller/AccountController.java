package com.controller;

import com.dao.AccountDAO;
import com.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    AccountDAO dao;

    @RequestMapping("/account/index")
    public String index(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        List<Account> accounts = dao.findAll();
        model.addAttribute("accounts", accounts);
        return "account/index";
    }

    @RequestMapping("/account/edit/{username}")
    public String edit(Model model, @PathVariable("username") String username) {
        Account account = dao.findByUsername(username).get();
        model.addAttribute("account", account);
        List<Account> accounts = dao.findAll();
        model.addAttribute("accounts", accounts);
        return "account/index";
    }

    @RequestMapping("/account/create")
    public String create(Account account) {
        dao.save(account);
        return "redirect:/account/index";
    }

    @RequestMapping("/account/update")
    public String update(Account account) {
        dao.save(account);
        return "redirect:/account/edit/" + account.getUsername();
    }

    @RequestMapping("/account/delete/{username}")
    public String create(@PathVariable("username") String username) {
        dao.deleteByUsername(username);
        return "redirect:/account/index";
    }
}
