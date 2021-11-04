package com.rafaelhosaka.ecomm.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void saveAll(Collection<Shop> shops){
        shopRepository.saveAll(shops);
    }

    public void save(Shop shop) {
        shopRepository.save(shop);
    }

    public List<Shop> getAllProducts() {
        return shopRepository.findAll();
    }
}
