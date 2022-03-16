package com.rafaelhosaka.ecomm.advice;

import com.rafaelhosaka.ecomm.auth.ApplicationUser;
import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalController {

    private final CategoryService categoryService;
    private final BuyerService buyerService;

    @Autowired
    public GlobalController(CategoryService categoryService, BuyerService buyerService) {
        this.categoryService = categoryService;
        this.buyerService = buyerService;
    }

    @ModelAttribute("loggedBuyer")
    public Buyer getLoggedBuyer(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof ApplicationUser){
            ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return buyerService.getBuyerByEmail(applicationUser.getUsername());
        }
        return null;
    }

    @ModelAttribute("sessionCategories")
    public List<Category> loadCategories() {
        return  categoryService.findAll();
    }
}