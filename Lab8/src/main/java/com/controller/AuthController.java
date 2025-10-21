package com.controller;

import com.entity.Account;
import com.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    @GetMapping("/auth/login")
    public String loginForm(Model model) {
        model.addAttribute(
                "redirect",
                Optional.ofNullable((String) session.getAttribute("securityUri"))
                        .orElse("No redirect uris"));
        return "/auth/login";
    }

    @PostMapping("/auth/login")
    public String loginProcess(
            Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        Account user = accountService.findById(username);
        if(user == null) {
            model.addAttribute("message", "Invalid email!");
        } else if(!user.getPassword().equals(password)) {
            model.addAttribute("message", "Invalid password!");
        } else{
            session.setAttribute("user", user);
            model.addAttribute("message", "Login successfully!");
            String securityUri = (String)session.getAttribute("securityUri");
            if(securityUri != null) {
                return "redirect:" + securityUri;
            }
        }
        return "/auth/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        session.removeAttribute("user");
        return "redirect:/auth/login";
    }

    @GetMapping({
            "/account/{accountVariable}",
            "/order/{orderVariable}",
            "/admin/{adminVariable}",
            "/admin/{adminVariable}/index"
    })
    public String authTestPages(
            @PathVariable(required = false) String accountVariable,
            @PathVariable(required = false) String orderVariable,
            @PathVariable(required = false) String adminVariable,
            HttpServletRequest request,
            Model model
    ) {
        String path = request.getRequestURI(); // e.g. /account/cart

        String pageType = "";
        String subPage = "";

        if (path.startsWith("/account/")) {
            pageType = "Account page";
            subPage = accountVariable;
        } else if (path.startsWith("/order/")) {
            pageType = "Order page";
            subPage = orderVariable;
        } else if (path.startsWith("/admin/")) {
            pageType = "Admin page";
            subPage = adminVariable;
        }

        model.addAttribute("path", path);
        model.addAttribute("title", pageType + ": " + subPage);
        model.addAttribute("user", Optional.ofNullable((Account) session.getAttribute("user")).map(Account::getFullname).orElse("not logged in"));

        return "/auth/testpage";
    }
}
