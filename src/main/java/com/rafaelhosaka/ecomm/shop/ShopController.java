package com.rafaelhosaka.ecomm.shop;

import com.rafaelhosaka.ecomm.auth.ApplicationUser;
import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import com.rafaelhosaka.ecomm.exception.ShopNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping("/shop")
@SessionAttributes("sessionBuyer")//Need to know which buyer will open the shop
public class ShopController {
    private final ShopService shopService;
    private final BuyerService buyerService;

    @Autowired
    public ShopController(ShopService shopService, BuyerService buyerService) {
        this.shopService = shopService;
        this.buyerService = buyerService;
    }

    @Secured({"ROLE_ADMIN","ROLE_BUYER"})
    @GetMapping("/showNewShopForm/{id}")
    public String showNewShopForm(@PathVariable("id") Long buyerId, Model model) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        buyer.setShop(new Shop());
        model.addAttribute("sessionBuyer", buyer);//save in the session
        return "/buyer/add-shop-form";
    }

    @Secured({"ROLE_ADMIN","ROLE_BUYER"})
    @PostMapping("/save")
    public String insertBuyer(@ModelAttribute("sessionBuyer") Buyer buyer, RedirectAttributes redirectAttributes){
        buyerService.save(buyer);
        redirectAttributes.addFlashAttribute("successAlert","The shop has been opened for "+buyer.getFirstName()+" successfully");
        return "redirect:/index";
    }

    @Secured({"ROLE_ADMIN","ROLE_BUYER"})
    @PostMapping("/update/{id}")
    public String updateShop(@PathVariable("id") Long shopId, Buyer buyer, Model model){
        try {
            shopService.updateShop(shopId, buyer.getShop());
            model.addAttribute("successAlert", "Edited information saved successfully!");
        }catch (ShopNotFoundException e){
            model.addAttribute("errorAlert", e.getMessage());
        }
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Buyer loggedBuyer = buyerService.getBuyerByEmail(applicationUser.getUsername());
        model.addAttribute("buyer",loggedBuyer);
        return "/buyer/buyer-home-page";
    }

}
