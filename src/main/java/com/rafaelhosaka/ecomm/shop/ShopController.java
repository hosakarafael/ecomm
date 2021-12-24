package com.rafaelhosaka.ecomm.shop;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    private final BuyerService buyerService;

    @Autowired
    public ShopController(ShopService shopService, BuyerService buyerService) {
        this.shopService = shopService;
        this.buyerService = buyerService;
    }

    /**
     * Prepare and show form to add new shop
     *
     * @param model
     * @return path to shop/addForm.html
     */
    @GetMapping("/showNewShopForm/{id}")
    public String showNewShopForm(@PathVariable("id") Long buyerId, Model model) {
        model
                .addAttribute("title", "Add New Shop")
                .addAttribute("buyer", buyerService.getBuyerById(buyerId));
        return "/shop/addForm";
    }

}
