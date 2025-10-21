package com.controller;

import com.entity.Staff;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class StaffController {
    protected final List<Staff> list = List.of(
        Staff.builder().id("user1@gmail.com").fullname("nguyễn văn user1").level(0).build(),
        Staff.builder().id("user2@gmail.com").fullname("nguyễn văn user2").level(1).build(),
        Staff.builder().id("user3@gmail.com").fullname("nguyễn văn user3").level(2).build(),
        Staff.builder().id("user4@gmail.com").fullname("nguyễn văn user4").level(2).build(),
        Staff.builder().id("user5@gmail.com").fullname("nguyễn văn user5").level(1).build(),
        Staff.builder().id("user6@gmail.com").fullname("nguyễn văn user6").level(0).build());


    @RequestMapping("/staff/detail")
    public String detail(Model model) {
        Staff staff = Staff.builder()
                .id("user@gmail.com")
                .fullname("nguyễn văn user")
                .level(2)
                .build();
        model.addAttribute("staff", staff);
        return "/demo/staff-detail";
    }

    @RequestMapping("/staff/list")
    public String list(Model model) {
            model.addAttribute("list", list);
        return "/demo/staff-list";
    }

    @RequestMapping("/staff/table")
    public String table(Model model) {
        model.addAttribute("list", list);
        return "/demo/list-status";
    }

    @RequestMapping("/staff/dropdown")
    public String dropdown(Model model) {
        model.addAttribute("list", list);
        return "/demo/list-controls";
    }
}
