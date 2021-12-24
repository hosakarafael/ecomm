package com.rafaelhosaka.ecomm.main;

import com.rafaelhosaka.ecomm.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private final ShopService shopService;

    @Autowired
    public MainController(ShopService shopService) {
        this.shopService = shopService;
    }

    @RequestMapping(value={"/","","/index"})
    public String showHomePage(Model model){
        model.addAttribute("title","Home");
        return "/index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title","Login");
        return "/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("title","Login");
        model.addAttribute("errorAlert", "Wrong user or password");

        return "/login";
    }
}
