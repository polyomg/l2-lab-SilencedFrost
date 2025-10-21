package com.controller;

import com.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MailController {
    @Autowired
    MailService mailService;

    @ResponseBody
    @RequestMapping("/mail/send")
    public String send(Model model) {
        mailService.push("thnrgbefv0987@gmail.com", "Mail chờ", "Mail này đã chờ ");
        return "Mail của bạn đã được xếp vào hàng đợi";
    }

    @GetMapping("/mail/form")
    public String sendForm() {
        return "/mail/mailform.html";
    }

    @PostMapping("/mail/process")
    public String processMailForm(
            @ModelAttribute MailService.Mail mail,
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("action") String action,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        List<String> savedFiles = new ArrayList<>();
        Path uploadPath = Paths.get("src/main/resources/static/uploads");
        Files.createDirectories(uploadPath);

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Path dest = uploadPath.resolve(file.getOriginalFilename());
                file.transferTo(dest);
                savedFiles.add(dest.toAbsolutePath().toString());
            }
        }

        mail.setFilenames(String.join(",", savedFiles));

        if ("send".equals(action)) {
            mailService.send(mail);
            redirectAttributes.addFlashAttribute("message", "mail sent");
        } else if ("queue".equals(action)) {
            mailService.push(mail);
            redirectAttributes.addFlashAttribute("message", "mail queued");
        }

        return "redirect:/mail/form";
    }
}
