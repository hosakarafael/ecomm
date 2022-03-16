package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.auth.ApplicationUser;
import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BuyerService buyerService;

    @Autowired
    public ProductController(ProductService productService,BuyerService buyerService,CategoryService categoryService) {
        this.productService = productService;
        this.buyerService = buyerService;
        this.categoryService = categoryService;
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

    @GetMapping("/showEditProduct/{id}")
    public String showEditProduct(@PathVariable("id")Long productId, Model model){
        model.addAttribute("product",productService.findEnabledById(productId));
        return "/buyer/edit-product";
    }

    @PostMapping("/update")
    public String updateProduct(Product product, Model model){
        product.setCategory(categoryService.findById(product.getCategory().getId()));
        productService.update(product.getId(),product);
        model.addAttribute("successAlert","Product updated!");
        model.addAttribute("activeTab","product");

        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Buyer loggedBuyer = buyerService.getBuyerByEmail(applicationUser.getUsername());

        model.addAttribute("buyer",loggedBuyer);
        return "/buyer/buyer-home-page";
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
            throws ServletException {

        // Convert multipart object to byte[]
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

    }
}
