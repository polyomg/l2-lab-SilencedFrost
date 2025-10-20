package com.controller;

import com.dao.ProductDAO;
import com.entity.Product;
import jakarta.servlet.http.HttpSession;
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

@Controller
public class ProductController {
    @Autowired
    ProductDAO dao;

    @Autowired
    HttpSession session;

    @RequestMapping("/product/sort")
    public String sort(Model model, @RequestParam("field") Optional<String> field) {
        boolean sortOrder = Optional.ofNullable(session.getAttribute("order")).map(Object::toString).map(Boolean::parseBoolean).orElse(true);

        if(field.isPresent()) {
            if(field.get().equals(session.getAttribute("field"))) {
                sortOrder = !sortOrder;
            } else {
                sortOrder = true;
            }
            session.setAttribute("order", sortOrder);
            session.setAttribute("field", field.get());
        }

        String sortValue = Optional.ofNullable(session.getAttribute("field")).map(Object::toString).orElse("price");

        Sort sort = Sort.by(sortOrder? Sort.Direction.DESC : Sort.Direction.ASC, sortValue);
        model.addAttribute("field", sortValue.toUpperCase());

        List<Product> items = dao.findAll(sort);
        model.addAttribute("items", items);

        return "product/sort";
    }

    @RequestMapping("/product/page")
    public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Product> page = dao.findAll(pageable);
        model.addAttribute("page", page);
        return "product/page";
    }

    @RequestMapping("/product/sort-page")
    public String sortedPage(Model model, @RequestParam("field") Optional<String> field, @RequestParam("p") Optional<Integer> p) {
        boolean sortOrder = Optional.ofNullable(session.getAttribute("order")).map(Object::toString).map(Boolean::parseBoolean).orElse(true);

        if(field.isPresent()) {
            if(field.get().equals(session.getAttribute("field"))) {
                sortOrder = !sortOrder;
            } else {
                sortOrder = true;
            }
            session.setAttribute("order", sortOrder);
            session.setAttribute("field", field.get());
        }

        String sortValue = Optional.ofNullable(session.getAttribute("field")).map(Object::toString).orElse("price");

        Pageable pageable = PageRequest.of(
                p.orElse(0),
                5,
                Sort.by(
                        sortOrder? Sort.Direction.DESC : Sort.Direction.ASC,
                        sortValue
                )
        );

        model.addAttribute("field", sortValue.toUpperCase());
        model.addAttribute("order", sortOrder? "DESC" : "ASC");

        Page<Product> page = dao.findAll(pageable);
        model.addAttribute("page", page);

        return "product/sortedpage";
    }
}
