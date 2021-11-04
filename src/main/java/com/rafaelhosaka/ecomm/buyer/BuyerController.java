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

    @GetMapping("/list")
    public String getBuyers(Model model){
        model
                .addAttribute("title","Buyer List")
                .addAttribute("buyers",buyerService.getAllBuyers());

        return "/buyer/list";
    }

    @GetMapping("/showNewBuyerForm")
    public String showNewBuyerForm(Model model){
        model
                .addAttribute("title","Add New Buyer")
                .addAttribute("buyer",new Buyer());
        return "/buyer/addForm";
    }

    @GetMapping("/showEditBuyerForm/{id}")
    public String showEditBuyerForm(@PathVariable("id") Long buyerId, Model model, RedirectAttributes redirectAttributes){
        try {
            Buyer buyer = buyerService.getBuyerById(buyerId);
            model
                    .addAttribute("title","Edit Buyer")
                    .addAttribute("buyer",buyer);
            return "/buyer/addForm";
        } catch (BuyerNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorAlert",e.getMessage());
            return "redirect:/buyer/list";
        }
    }

    @PostMapping("/save")
    public String insertBuyer(Buyer buyer, RedirectAttributes redirectAttributes){
        buyerService.save(buyer);
        redirectAttributes.addFlashAttribute("successAlert","The buyer has been saved successfully");
        return "redirect:/buyer/list";
    }

    @PutMapping("/update")
    public void updateBuyer(Buyer buyer){
        buyerService.updateBuyer(buyer.getId(),buyer.getFirstName(),buyer.getEmail());
    }

    @GetMapping("/delete/{id}")
    public String deleteBuyer(@PathVariable("id") Long buyerId, RedirectAttributes redirectAttributes){
        buyerService.deleteBuyer(buyerId);
        redirectAttributes.addFlashAttribute("successAlert","The buyer has been deleted successfully");
        return "redirect:/buyer/list";
    }

}
