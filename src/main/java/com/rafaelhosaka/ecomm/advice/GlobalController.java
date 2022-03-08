package com.rafaelhosaka.ecomm.advice;

import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalController {

    private final CategoryService categoryService;

    @Autowired
    public GlobalController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute("sessionCategories")
    public List<Category> loadCategories() {
        return  categoryService.findAll();
    }
}