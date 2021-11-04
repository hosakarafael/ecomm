package com.rafaelhosaka.ecomm.config;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerRepository;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.product.Category;
import com.rafaelhosaka.ecomm.product.Product;
import com.rafaelhosaka.ecomm.shop.Shop;
import com.rafaelhosaka.ecomm.shop.ShopService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import  java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class Bootstrap {
    @Bean
    CommandLineRunner commandLineRunner(BuyerService buyerService, ShopService shopService){
        return args -> {
            configureBuyers(buyerService);
            configureShops(buyerService);
            configureProducts(shopService);
        };
    }

    public void configureBuyers(BuyerService buyerService){

        Buyer ahri = new Buyer("Ahri","Vastaya" , "Ionia", "1111111",
                LocalDate.of(2000, Month.JANUARY,1),"ahri@lol",
                "????");


        Buyer braum = new Buyer("Braum","Bro","Freljorld", "22222222",
                LocalDate.of(2000, Month.JANUARY,1),"braum@lol",
                "????");

        buyerService.saveAll(List.of(ahri,braum));

    }

    public void configureShops(BuyerService buyerService){
        List<Buyer> buyers = buyerService.getAllBuyers();

        //TODO test if saving product is enough
        for (Buyer buyer: buyers) {
            Shop shop = new Shop("shop of "+buyer.getFirstName(),"description",
                    "phoneNumber","address",buyer);
            buyer.setShop(shop);
            buyerService.save(buyer);
        }
    }

    public void configureProducts(ShopService shopService){
        List<Shop> shops = shopService.getAllProducts();

        for (Shop shop: shops) {
            Product product = new Product("name", "desciption", 123f, 1, Category.ACCESSORY);
            shop.setProduct(product);
            shopService.save(shop);
        }
    }
}
