package com.controller;

import com.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {
    List<Product> products = new ArrayList<>(Arrays.asList(new Product("A", Double.toString(1)), new Product("B", Double.toString(12))));

    @GetMapping("/product/form")
    public String form(Model model) {
        Product p = new Product();
        p.setName("iPhone 30");
        p.setPrice(Double.toString(5000));
        model.addAttribute(p);
        return "product/form";
    }

    @PostMapping("/product/save")
    public String save(@ModelAttribute Product p, RedirectAttributes redirectAttributes) {
        products.add(p);
        redirectAttributes.addFlashAttribute("p", p);
        return "redirect:/product/form";
    }

    @ModelAttribute("items")
    public List<Product> getItems() {
        return products;
    }
}
