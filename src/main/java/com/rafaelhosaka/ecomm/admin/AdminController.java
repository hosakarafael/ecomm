package com.rafaelhosaka.ecomm.admin;

import com.rafaelhosaka.ecomm.auth.ApplicationUserService;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final BuyerService buyerService;

    @Autowired
    public AdminController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteBuyer(@PathVariable("id") Long buyerId, RedirectAttributes redirectAttributes){
        try {
            buyerService.deleteBuyer(buyerId);
            redirectAttributes.addFlashAttribute("successAlert","The buyer has been deleted successfully");
        } catch (BuyerNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorAlert",e.getMessage());
        }
        return "redirect:/admin/buyers-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/list")
    public String getBuyers(Model model){
        model.addAttribute("buyers",buyerService.getAllBuyers());

        return "/admin/buyers-list";
    }
}
