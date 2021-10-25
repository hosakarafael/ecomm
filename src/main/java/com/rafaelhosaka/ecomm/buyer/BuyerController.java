package com.rafaelhosaka.ecomm.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(path = "api/buyer")
public class BuyerController {

    private final BuyerService buyerService;

    @Autowired
    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @GetMapping(path = "/list")
    public String getBuyers(ModelMap model){
        model.addAttribute("buyers",buyerService.getAllBuyers());
        return "/buyer/buyerList";
    }

    @PostMapping
    public void insertBuyer(@RequestBody Buyer buyer){
        buyerService.insertBuyer(buyer);
    }

    @PutMapping(path = "{buyerId}")
    public void updateBuyer(@PathVariable("buyerId") Long buyerId,
                            @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "email", required = false) String email){
        buyerService.updateBuyer(buyerId,name,email);
    }

    @DeleteMapping(path="{buyerId}")
    public void deleteBuyer(@PathVariable("buyerId") Long buyerId){
        buyerService.deleteBuyer(buyerId);
    }

}
