package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.auth.ApplicationUser;
import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final BuyerService buyerService;

    @Autowired
    public ProductController(ProductService productService,BuyerService buyerService) {
        this.productService = productService;
        this.buyerService = buyerService;
    }

    @GetMapping("/productInfo/{id}")
    public String showProductInfo(@PathVariable("id") Long productId, Model model){
        model.addAttribute("product", productService.findById(productId));
        return "/product-info";
    }

    @GetMapping("/enableProduct/{enable}/{id}")
    public String enableProduct(@PathVariable("id") Long productId, @PathVariable("enable") boolean enable, Model model){
        Product product = productService.findById(productId);
        if(true){ //TODO implement password update request
            product.setEnabled(enable);
            productService.save(product);
            model.addAttribute("successAlert","Product "+ (enable ? " enabled " : "disabled") +" successfully!");
        }
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Buyer loggedBuyer = buyerService.getBuyerByEmail(applicationUser.getUsername());

        model.addAttribute("buyer",loggedBuyer);
        model.addAttribute("activeTab","product");
        return "/buyer/buyer-home-page";
    }
}
