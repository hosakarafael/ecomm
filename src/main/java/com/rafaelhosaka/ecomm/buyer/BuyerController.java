package com.rafaelhosaka.ecomm.buyer;

import com.google.common.collect.Lists;
import com.rafaelhosaka.ecomm.account.UserAccount;
import com.rafaelhosaka.ecomm.account.UserRole;
import com.rafaelhosaka.ecomm.auth.ApplicationUser;
import com.rafaelhosaka.ecomm.auth.ApplicationUserService;
import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

    /**
     * Get all Buyers and show in a list
     * @param model
     * @return path to buyer/buyers-list.html
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/list")
    public String getBuyers(Model model){
        model.addAttribute("buyers",buyerService.getAllBuyers());

        return "/admin/buyer-list";
    }

    /**
     * Prepare and show form to add new buyer
     * @param model
     * @return path to buyer/add-shop-form.html
     */
    @GetMapping("/showNewBuyerForm")
    public String showNewBuyerForm(Model model){
        model
                .addAttribute("title","Create account")
                .addAttribute("buyer",new Buyer());
        return "/add-buyer-form";
    }

    /**
     * Prepare and show Form to Edit the Buyer information
     * @param buyerId
     * @param model
     * @param redirectAttributes
     * @return redirect to buyer/buyers-list.html
     */
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
    public String insertBuyer(Buyer buyer, RedirectAttributes redirectAttributes){
        buyerService.save(buyer);
        applicationUserService.createUser(new ApplicationUser(
                new UserAccount(buyer.getEmail(),
                        buyer.getPassword(),
                        true,
                        Lists.newArrayList(UserRole.BUYER)
                )));
        redirectAttributes.addFlashAttribute("successAlert","Account created successfully");
        return "redirect:/login";
    }

    /**
     * Delete buyer
     * @param buyerId
     * @param redirectAttributes
     * @return redirect to buyer/buyers-list.html
     */
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
        return "redirect:/buyer/buyers-list";
    }

    /**
     * Prepare and show data of buyer
     * @param buyerId
     * @param model
     * @param redirectAttributes
     * @return path to buyer/show-buyer-info.html
     */
    @Secured({"ROLE_ADMIN","ROLE_BUYER"})
    @GetMapping("/showBuyerInfo/{id}")
    public String showBuyerInfo(@PathVariable("id") Long buyerId, Model model, RedirectAttributes redirectAttributes){
            Buyer buyer = buyerService.getBuyerById(buyerId);
            model.addAttribute("buyer",buyer);
            return "/buyer/show-buyer-info";
    }

}
