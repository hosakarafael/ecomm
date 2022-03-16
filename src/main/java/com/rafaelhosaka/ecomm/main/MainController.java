package com.rafaelhosaka.ecomm.main;

import com.rafaelhosaka.ecomm.cart.Cart;
import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.category.CategoryService;
import com.rafaelhosaka.ecomm.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class MainController {

    private final ProductService productService;
    private Cart sessionCart;

    @Autowired
    public MainController(ProductService productService, Cart cart) {
        this.productService = productService;
        this.sessionCart = cart;
    }

    @RequestMapping(value={"/","","/index"})
    public String showHomePage(Model model, HttpSession session){
        model.addAttribute("products",productService.findAllEnabled());

        session.setAttribute("sessionCart",sessionCart);

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

    @GetMapping("/invalid-session")
    public String invalidSession(){
        return "invalid-session";
    }


    @GetMapping("/addCart/{id}")
    public String addCart(@PathVariable("id") Long productId, Model model) {
        sessionCart.add(productService.findEnabledById(productId));
        model.addAttribute("products", productService.findAllEnabled());
        model.addAttribute("successAlert", "Added to cart successfully!");
        return "/index";
    }

    @GetMapping("/removeCart/{id}")
    public String removeCart(@PathVariable("id") Long productId, Model model){
        sessionCart.remove(productService.findEnabledById(productId));
        return "/shopping-cart-info";
    }

    @PostMapping("/updateCart/{id}")
    public String updateCart(@PathVariable("id") Long productId,@ModelAttribute("quantity")Integer newQuantity, Model model){
        sessionCart.update(productService.findEnabledById(productId), newQuantity);
        model.addAttribute("successAlert","Cart updated!");
        return "/shopping-cart-info";
    }

    @GetMapping("/cartInfo")
    public String showCartInfo(){
        return "/shopping-cart-info";
    }

    @GetMapping("/search")
    public String searchProductsByCategory(@ModelAttribute("categoryId")Long categoryId,@ModelAttribute("searchText") String searchText, Model model){
        if(searchText.isEmpty()){
            if(categoryId != 0){
                model.addAttribute("products",productService.findEnabledByCategoryId(categoryId));
                model.addAttribute("selectedCategoryId",categoryId);
            }else{
                model.addAttribute("products",productService.findAllEnabled());
            }
        }else{
            if(categoryId != 0){
                model.addAttribute("products",productService.findEnabledByNameAndCategoryId(searchText,categoryId));
                model.addAttribute("selectedCategoryId",categoryId);
            }else{
                model.addAttribute("products",productService.findEnabledByName(searchText));
            }
        }

        return "/index";
    }

}
