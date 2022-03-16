package com.rafaelhosaka.ecomm.order;

import com.rafaelhosaka.ecomm.cart.Cart;
import com.rafaelhosaka.ecomm.product.ProductService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @Secured("ROLE_BUYER")
    @GetMapping("/showCheckout")
    public String checkout(Model model){
        model.addAttribute("products",productService.findAllEnabled());
        return "/buyer/checkout";
    }

    @PostMapping("/checkout")
    public String saveOrder(@ModelAttribute("paymentMethod")String paymentMethod, @ModelAttribute("radioAddress") String address,Model model, HttpSession session){
        Cart sessionCart = (Cart)session.getAttribute("sessionCart");
        orderService.save(sessionCart, paymentMethod, address);
        model.addAttribute("successAlert","Order placed!");
        model.addAttribute("products",productService.findAllEnabled());
        sessionCart.resetCart();

        return "/index";
    }
}
