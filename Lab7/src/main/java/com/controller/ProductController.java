package com.controller;

import com.dao.ProductDAO;
import com.entity.Product;
import com.entity.Report;
import com.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {
    public final ProductDAO productDAO;
    public final SessionService sessionService;

    @RequestMapping("/product/search")
    public String search(
            Model model,
            @RequestParam("min") Optional<Double> min,
            @RequestParam("max") Optional<Double> max) {
        double minPrice = min.orElse(Double.MIN_VALUE);
        double maxPrice = max.orElse(Double.MAX_VALUE);
//        List<Product> items = productDAO.findByPrice(minPrice, maxPrice);
        List<Product> items = productDAO.findByPriceBetween(minPrice, maxPrice);
        model.addAttribute("items", items);
        return "product/search";
    }

    @RequestMapping("/product/search-and-page")
    public String searchAndPage(Model model,
                                @RequestParam("keywords") Optional<String> kw,
                                @RequestParam("p") Optional<Integer> p) {
        String kwords = kw.orElse(sessionService.get("keywords"));
        sessionService.set("keywords", kwords);
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
//        Page<Product> page = productDAO.findByKeywords("%" + kwords + "%", pageable);
        Page<Product> page = productDAO.findAllByNameLike("%" + kwords + "%", pageable);
        model.addAttribute("page", page);
        return "product/search-and-page";
    }

    @RequestMapping("/report/inventory-by-category")
    public String inventory(Model model) {
        List<Report> items = productDAO.getInventoryByCategory();
        model.addAttribute("items", items);
        return "report/inventory-by-category";
    }
}
