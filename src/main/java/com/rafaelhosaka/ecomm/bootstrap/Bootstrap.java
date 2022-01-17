package com.rafaelhosaka.ecomm.bootstrap;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rafaelhosaka.ecomm.auth.ApplicationUser;
import com.rafaelhosaka.ecomm.auth.ApplicationUserService;
import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.product.Category;
import com.rafaelhosaka.ecomm.product.Product;
import com.rafaelhosaka.ecomm.shop.Shop;
import com.rafaelhosaka.ecomm.shop.ShopService;
import com.rafaelhosaka.ecomm.account.UserAccount;
import com.rafaelhosaka.ecomm.account.UserRole;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

/**
 * Class responsible for configuring all data in the database for initial uses
 */
@Configuration
public class Bootstrap {

    @Bean
    CommandLineRunner commandLineRunner(BuyerService buyerService, ShopService shopService , ApplicationUserService applicationUserService){
        return args -> {
            configureBuyers(buyerService, applicationUserService);
            configureShops(buyerService);
            configureProducts(shopService);
        };
    }

    /**
     * Generate datas of buyers in the database
     * @param buyerService
     */
    public void configureBuyers(BuyerService buyerService, ApplicationUserService applicationUserService){

        configureAccount(applicationUserService, "admin", "admin", true, Lists.newArrayList(UserRole.ADMIN));

        Buyer ahri = new Buyer("Ahri","Vastaya" , "Ionia", "1111111",
                LocalDate.of(2000, Month.JANUARY,1),"ahri@lol",
                "????");
        configureAccount(applicationUserService, ahri.getEmail(), ahri.getPassword(), true, Lists.newArrayList(UserRole.BUYER));


        Buyer braum = new Buyer("Braum","Bro","Freljorld", "22222222",
                LocalDate.of(2000, Month.JANUARY,1),"braum@lol",
                "????");
        configureAccount(applicationUserService, braum.getEmail(), braum.getPassword(), true, Lists.newArrayList(UserRole.BUYER));



        buyerService.saveAll(List.of(ahri,braum));

    }

    /**
     * Generate datas of shops for all buyers registered in the database
     * @param buyerService
     */
    public void configureShops(BuyerService buyerService){
        List<Buyer> buyers = buyerService.getAllBuyers();

        for (Buyer buyer: buyers) {
            Shop shop = new Shop("shop of "+buyer.getFirstName(),"description",
                    "phoneNumber","address",buyer);
            buyer.setShop(shop);
            buyerService.save(buyer);
        }
    }

    /**
     * Generate Products for all shop registered in the database
     * @param shopService
     */
    public void configureProducts(ShopService shopService) throws IOException {
        List<Shop> shops = shopService.getAllShops();
        Set<Product> products = Sets.newHashSet();

        for (Shop shop: shops) {
            for (int i = 0 ; i < 3; i++) {
                Product product = new Product("product "+i+" of "+shop.getBuyer().getFirstName(), "description "+i, 123f, 1, Category.ACCESSORY, shop);
                product.setMainImage(retrieveSampleImage("static/images/hd.jpeg").getBytes());
                products.add(product);
            }
            shop.setProducts(products);
            shopService.save(shop);
            products = Sets.newHashSet();
        }
    }

    public MultipartFile retrieveSampleImage(String filePath) throws IOException {
        Resource resource = new ClassPathResource(filePath);

        FileInputStream input = new FileInputStream(resource.getFile());
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                resource.getFilename(), "image/png", IOUtils.toByteArray(input));

        return multipartFile;
    }

    public void configureAccount(ApplicationUserService applicationUserService ,String email, String password,boolean isActive, List<UserRole> roles){
        applicationUserService.createUser(
                new ApplicationUser(
                        new UserAccount(email,
                                password,
                                isActive,
                                roles
                        )
                )
        );
    }
}
