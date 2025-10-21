package com.controller;

import com.service.CookieService;
import com.service.ParamService;
import com.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final CookieService cookieService;
    private final ParamService paramService;
    private final SessionService sessionService;

    @GetMapping("/account/login")
    public String login1() {
        return "/account/login";
    }
    @PostMapping("/account/login")
    public String login2() {
        String un = paramService.getString("username", "");
        String pw = paramService.getString("password", "");
        boolean rm = paramService.getBoolean("remember", false);
        if(un.equals("poly") && pw.equals("123")) {
            sessionService.set("username", un);
            if(rm) {
                cookieService.add("user", un, (int) Duration.ofDays(10).toHours());
            } else {
                cookieService.remove("user");
            }
        }
        return "redirect:/account/login";
    }
}