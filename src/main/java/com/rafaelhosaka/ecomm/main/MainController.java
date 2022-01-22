package com.rafaelhosaka.ecomm.main;

import com.rafaelhosaka.ecomm.product.Product;
import com.rafaelhosaka.ecomm.product.ProductService;
import com.rafaelhosaka.ecomm.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final ProductService productService;

    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value={"/","","/index"})
    public String showHomePage(Model model){
        model.addAttribute("products",productService.findAll());
        return "/index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "/login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("errorAlert", "Wrong email or password, please try again");

        return "/login";
    }
}
