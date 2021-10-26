package com.rafaelhosaka.ecomm.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class BuyerService{
    private final BuyerDao buyerDao;

    @Autowired
    public BuyerService(BuyerDao buyerDao) {
        this.buyerDao = buyerDao;
    }

    public List<Buyer> getAllBuyers(){
        return buyerDao.findAll();
    }

    public void deleteBuyer(Long buyerId) {
        if(buyerDao.existsById(buyerId)){
            buyerDao.deleteById(buyerId);
        }else{
            throw new IllegalStateException("Buyer with ID "+buyerId+" does not exist");
        }

    }

    public void insertBuyer(Buyer buyer) {
        buyerDao.save(buyer);
    }

    @Transactional
    public void updateBuyer(Long buyerId, String name, String email) {
        Buyer buyer = buyerDao.findById(buyerId).orElseThrow(
                () -> new IllegalStateException("Buyer with ID "+buyerId+" does not exist")
        );

        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(buyer.getFirstName(),name)){
            buyer.setFirstName(name);
        }

        if(email != null &&
                email.length() > 0 &&
        !Objects.equals(buyer.getEmail(),email)){
            buyer.setEmail(email);
        }else{
            throw new IllegalStateException("Email taken");
        }


    }
}
