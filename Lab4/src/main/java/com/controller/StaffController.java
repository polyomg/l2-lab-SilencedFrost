package com.controller;

import com.entity.Staff;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/staff/create")
public class StaffController {
    @GetMapping("/form")
    public String createForm(Model model, @ModelAttribute("staff") Staff staff) {
        model.addAttribute("message", "Vui lòng nhập thông tin nhân viên!");
        return "/demo/staff-validate";
    }

    @PostMapping("/save")
    public String createSave(
            Model model,
            @RequestPart("photo_file") MultipartFile photoFile,
            @Valid @ModelAttribute("staff") Staff staff, Errors errors) {
        if(!photoFile.isEmpty()) {
            staff.setPhoto(photoFile.getName());
        }
        if(errors.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
        } else {
            model.addAttribute("message", "Dữ liệu đã nhập đúng!");
        }
        return "/demo/staff-validate";
    }
}