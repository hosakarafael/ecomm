package com.rafaelhosaka.ecomm.buyer;

import com.google.common.collect.Lists;
import com.rafaelhosaka.ecomm.account.UserAccount;
import com.rafaelhosaka.ecomm.account.UserRole;
import com.rafaelhosaka.ecomm.auth.ApplicationUser;
import com.rafaelhosaka.ecomm.auth.ApplicationUserService;
import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import com.rafaelhosaka.ecomm.exception.UsernameDuplicatedException;
import com.rafaelhosaka.ecomm.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final BuyerService buyerService;

    private final ApplicationUserService applicationUserService;

    @Autowired
    public BuyerController(BuyerService buyerService, ApplicationUserService applicationUserService) {
        this.buyerService = buyerService;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/showNewBuyerForm")
    public String showNewBuyerForm(Model model){
        model
                .addAttribute("title","Create account")
                .addAttribute("buyer",new Buyer());
        return "/add-buyer-form";
    }

    @Secured({"ROLE_ADMIN","ROLE_BUYER"})
    @GetMapping("/showEditBuyerForm/{id}")
    public String showEditBuyerForm(@PathVariable("id") Long buyerId, Model model, RedirectAttributes redirectAttributes) {
            Buyer buyer = buyerService.getBuyerById(buyerId);
            model
                    .addAttribute("title","Edit Buyer")
                    .addAttribute("buyer",buyer);
            return "/add-buyer-form";
    }

    @PostMapping("/save")
    public String createBuyer(Buyer buyer, RedirectAttributes redirectAttributes){
        try {
            applicationUserService.createUser(new ApplicationUser(
                    new UserAccount(buyer.getEmail(),
                            buyer.getPassword(),
                            true,
                            Lists.newArrayList(UserRole.BUYER)
                    )));
            buyerService.save(buyer);
            redirectAttributes.addFlashAttribute("successAlert","Account created successfully");
        }catch (UsernameDuplicatedException e){
            redirectAttributes.addFlashAttribute("errorAlert","email taken");
            return showNewBuyerForm(redirectAttributes);
        }

        return "redirect:/login";
    }


    @Secured({"ROLE_ADMIN","ROLE_BUYER"})
    @GetMapping("/showBuyerInfo/{id}")
    public String showBuyerInfo(@PathVariable("id") Long buyerId, Model model){
            Buyer buyer = buyerService.getBuyerById(buyerId);
            model.addAttribute("buyer",buyer);
            return "/buyer/show-buyer-info";
    }

    @Secured("ROLE_BUYER")
    @GetMapping("/home")
    public String showBuyerHomePage(Model model){
        model.addAttribute("buyer",model.getAttribute("loggedBuyer"));
        model.addAttribute("activeTab","buyer");
        return "/buyer/buyer-home-page";
    }

    @Secured("ROLE_BUYER")
    @PostMapping("/update/{id}")
    public String updateBuyer(@PathVariable("id")Long id, Buyer buyer, Model model){
        try {
            buyerService.updateBuyer(id, buyer);
            model.addAttribute("successAlert","Edited information saved successfully!");
        }catch (IllegalStateException e) {
            model.addAttribute("errorAlert", e.getMessage());
        }

        model.addAttribute("activeTab","buyer");
        return "/buyer/buyer-home-page";
    }
}
