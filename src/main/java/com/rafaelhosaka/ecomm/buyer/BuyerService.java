package com.rafaelhosaka.ecomm.buyer;

import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BuyerService{
    private final BuyerRepository buyerRepository;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public void saveAll(Collection<Buyer> buyers){
        buyerRepository.saveAll(buyers);
    }

    public Buyer getBuyerById(Long id) throws BuyerNotFoundException {
        Optional<Buyer> resultOptional = buyerRepository.findById(id);
        if(resultOptional.isPresent()){
            return resultOptional.get();
        }
        throw new BuyerNotFoundException("204","Could not find any buyer with ID "+id);
    }

    public List<Buyer> getAllBuyers(){
        return buyerRepository.findAll();
    }

    public void deleteBuyer(Long buyerId) throws BuyerNotFoundException {
        if(buyerRepository.existsById(buyerId)){
            buyerRepository.deleteById(buyerId);
        }else{
            throw new BuyerNotFoundException("204","Buyer with ID "+buyerId+" does not exist");
        }

    }

    public void save(Buyer buyer) {
        buyerRepository.save(buyer);
    }

    @Transactional
    public void updateBuyer(Long buyerId, String name, String email) throws BuyerNotFoundException {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(
                () -> new BuyerNotFoundException("204","Buyer with ID "+buyerId+" does not exist")
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
