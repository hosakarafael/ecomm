package com.rafaelhosaka.ecomm.bootstrap;

import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rafaelhosaka.ecomm.auth.ApplicationUser;
import com.rafaelhosaka.ecomm.auth.ApplicationUserService;
import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.category.CategoryService;
import com.rafaelhosaka.ecomm.product.Product;
import com.rafaelhosaka.ecomm.shop.Shop;
import com.rafaelhosaka.ecomm.shop.ShopService;
import com.rafaelhosaka.ecomm.account.UserAccount;
import com.rafaelhosaka.ecomm.account.UserRole;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.*;

/**
 * Class responsible for configuring all data in the database for initial uses
 */
public class Bootstrap {

    private Faker faker = new Faker();
    final static int MAX_BUYER = 3;
    final static int MAX_PRODUCT = 3;

    CommandLineRunner commandLineRunner(BuyerService buyerService, ShopService shopService , ApplicationUserService applicationUserService, CategoryService categoryService){
        return args -> {
            configureBuyers(buyerService, applicationUserService);
            configureShops(buyerService);
            configureCategories(categoryService);
            configureProducts(shopService, categoryService);
        };
    }

    /**
     * Generate datas of buyers in the database
     * @param buyerService
     */
    public void configureBuyers(BuyerService buyerService, ApplicationUserService applicationUserService){

        configureAccount(applicationUserService, "admin", "admin", true, Lists.newArrayList(UserRole.ADMIN));

        Calendar pastDate = Calendar.getInstance();
        pastDate.set(1900,1,1);


        for(int i = 0; i < MAX_BUYER; i++){
            String firstName = faker.name().firstName();

            Buyer buyer = new Buyer(
                    firstName,
                    faker.name().lastName(),
                    faker.address().city(),
                    faker.phoneNumber().cellPhone(),
                    faker.date().between(pastDate.getTime(),  new Date())
                            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    firstName.toLowerCase()+"@ecomm.com");

            configureAccount(applicationUserService, buyer.getEmail(), "1234", true, Lists.newArrayList(UserRole.BUYER));

            buyerService.save(buyer);
        }

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

    private void configureCategories(CategoryService categoryService) {
        Set<Category> categories = new HashSet<Category>();
        categories.add(new Category("Electronics"));
        categories.add(new Category("Computers"));
        categories.add(new Category("Books"));

        categories.stream().forEach(category -> categoryService.save(category));
    }

    /**
     * Generate Products for all shop registered in the database
     * @param shopService
     * @param categoryService
     */
    public void configureProducts(ShopService shopService, CategoryService categoryService) throws IOException {
        List<Shop> shops = shopService.getAllShops();
        Set<Product> products = Sets.newHashSet();
        Category category = new Category();

        for (Shop shop: shops) {
            for (int i = 0 ; i < MAX_PRODUCT; i++) {

                int randomImageIndex = new Random().ints(1, MAX_PRODUCT+1).findFirst().getAsInt();

                switch (randomImageIndex){
                    case 1:
                        category = categoryService.findByName("Electronics");
                        break;
                    case 2:
                        category = categoryService.findByName("Computers");
                        break;
                    case 3 :
                        category = categoryService.findByName("Books");
                }

                Product product = new Product("product "+i+" of "+shop.getBuyer().getFirstName(),
                        faker.lorem().sentence(),
                        (float) faker.number().numberBetween(1,1000),
                        category ,
                        shop);
                product.setMainImage(retrieveSampleImage("static/img/" +randomImageIndex+".jpeg").getBytes());
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
