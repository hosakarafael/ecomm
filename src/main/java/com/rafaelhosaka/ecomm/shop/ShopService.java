package com.rafaelhosaka.ecomm.shop;

import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import com.rafaelhosaka.ecomm.exception.ShopNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Transactional
    public void updateShop(Long shopId, Shop newShop) throws ShopNotFoundException{
        Shop oldShop = shopRepository.findById(shopId).orElseThrow(
                () -> new ShopNotFoundException("205", "Shop with ID " + shopId + " does not exist")
        );

        if (newShop.getName() != null &&
                newShop.getName().length() > 0 &&
                Objects.equals(newShop.getName(), newShop.getName())) {
            oldShop.setName(newShop.getName());
        }

        if (newShop.getAddress() != null &&
                newShop.getAddress().length() > 0 &&
                Objects.equals(newShop.getAddress(), newShop.getAddress())) {
            oldShop.setAddress(newShop.getAddress());
        }

        if (newShop.getPhoneNumber() != null &&
                newShop.getPhoneNumber().length() > 0 &&
                Objects.equals(newShop.getPhoneNumber(), newShop.getPhoneNumber())) {
            oldShop.setPhoneNumber(newShop.getPhoneNumber());
        }

        if (newShop.getDescription() != null &&
                newShop.getDescription().length() > 0 &&
                Objects.equals(newShop.getDescription(), newShop.getDescription())) {
            oldShop.setDescription(newShop.getDescription());
        }
    }
}
