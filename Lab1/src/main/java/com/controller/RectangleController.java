package com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class RectangleController {

    @GetMapping("/rectangle")
    public String form() {
        return "rectangle";
    }

    @PostMapping("/rectangle/calculate")
    public String login(@RequestParam String height, @RequestParam String width, Model model) {

        boolean error = false;

        Float widthNum = null;
        Float heightNum = null;

        try {
            widthNum = Float.parseFloat(width);
            model.addAttribute("width", width);
        } catch (NumberFormatException ex) {
            model.addAttribute("widthError", "width is not a float");
            error = true;
        }

        try {
            heightNum = Float.parseFloat(height);
            model.addAttribute("height", height);
        } catch (NumberFormatException ex) {
            model.addAttribute("heightError", "height is not a float");
            error = true;
        }

        if(!error) {
            model.addAttribute("circumference", String.valueOf((widthNum + heightNum) * 2));
            model.addAttribute("area", String.valueOf(widthNum * heightNum));
        }
        return "rectangle";
    }
}
