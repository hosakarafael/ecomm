package com.rafaelhosaka.ecomm.buyer;

import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final BuyerService buyerService;

    @Autowired
    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    /**
     * Get all Buyers and show in a list
     * @param model
     * @return path to buyer/list.html
     */
    @GetMapping("/list")
    public String getBuyers(Model model){
        model
                .addAttribute("title","Buyer List")
                .addAttribute("buyers",buyerService.getAllBuyers());

        return "/buyer/list";
    }

    /**
     * Prepare and show form to add new buyer
     * @param model
     * @return path to buyer/addForm.html
     */
    @GetMapping("/showNewBuyerForm")
    public String showNewBuyerForm(Model model){
        model
                .addAttribute("title","Add New Buyer")
                .addAttribute("buyer",new Buyer());
        return "/buyer/addForm";
    }

    /**
     * Prepare and show Form to Edit the Buyer information
     * @param buyerId
     * @param model
     * @param redirectAttributes
     * @return redirect to buyer/list.html
     */
    @GetMapping("/showEditBuyerForm/{id}")
    public String showEditBuyerForm(@PathVariable("id") Long buyerId, Model model, RedirectAttributes redirectAttributes) {
            Buyer buyer = buyerService.getBuyerById(buyerId);
            model
                    .addAttribute("title","Edit Buyer")
                    .addAttribute("buyer",buyer);
            return "/buyer/addForm";
    }

    /**
     * Save new buyer
     * @param buyer
     * @param redirectAttributes
     * @return redirect to buyer/list.html
     */
    @PostMapping("/save")
    public String insertBuyer(Buyer buyer, RedirectAttributes redirectAttributes){
        buyerService.save(buyer);
        redirectAttributes.addFlashAttribute("successAlert","The buyer has been saved successfully");
        return "redirect:/buyer/list";
    }

    /**
     * Delete buyer
     * @param buyerId
     * @param redirectAttributes
     * @return redirect to buyer/list.html
     */
    @GetMapping("/delete/{id}")
    public String deleteBuyer(@PathVariable("id") Long buyerId, RedirectAttributes redirectAttributes){
        try {
            buyerService.deleteBuyer(buyerId);
            redirectAttributes.addFlashAttribute("successAlert","The buyer has been deleted successfully");
        } catch (BuyerNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorAlert",e.getMessage());
        }
        return "redirect:/buyer/list";
    }

    /**
     * Prepare and show data of buyer
     * @param buyerId
     * @param model
     * @param redirectAttributes
     * @return path to buyer/showInfo.html
     */
    @GetMapping("/showBuyerInfo/{id}")
    public String showBuyerInfo(@PathVariable("id") Long buyerId, Model model, RedirectAttributes redirectAttributes){
            Buyer buyer = buyerService.getBuyerById(buyerId);
            model
                    .addAttribute("title","Buyer Information")
                    .addAttribute("buyer",buyer);
            return "/buyer/showInfo";
    }

}
