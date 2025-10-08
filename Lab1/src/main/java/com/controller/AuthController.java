package com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AuthController {

    @GetMapping("/login/form")
    public String form() {
        return "login";
    }

    @PostMapping("/login/check")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if(username.equals("poly") && password.equals("123")) {
            model.addAttribute("returnMessage", "login successfully");
        } else {
            model.addAttribute("returnMessage", "login failed");
        }
        return "login";
    }
}
