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
public class BuyerService {
    private final BuyerRepository buyerRepository;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public void saveAll(Collection<Buyer> buyers) {
        buyerRepository.saveAll(buyers);
    }

    public Buyer getBuyerById(Long id) throws BuyerNotFoundException {
        Optional<Buyer> resultOptional = buyerRepository.findById(id);
        if (resultOptional.isPresent()) {
            return resultOptional.get();
        }
        throw new BuyerNotFoundException("204", "Could not find any buyer with ID " + id);
    }

    public List<Buyer> getAllBuyers() {
        return buyerRepository.findAll();
    }

    public void deleteBuyer(Long buyerId) throws BuyerNotFoundException {
        if (buyerRepository.existsById(buyerId)) {
            buyerRepository.deleteById(buyerId);
        } else {
            throw new BuyerNotFoundException("204", "Buyer with ID " + buyerId + " does not exist");
        }

    }

    public void save(Buyer buyer) {
        buyerRepository.save(buyer);
    }

    @Transactional
    public void updateBuyer(Long buyerId, Buyer newBuyer) throws BuyerNotFoundException {
        Buyer oldBuyer = buyerRepository.findById(buyerId).orElseThrow(
                () -> new BuyerNotFoundException("204", "Buyer with ID " + buyerId + " does not exist")
        );

        if (newBuyer.getEmail() != null &&
                newBuyer.getEmail().length() > 0 &&
                Objects.equals(oldBuyer.getEmail(), newBuyer.getEmail())) {
            oldBuyer.setEmail(newBuyer.getEmail());
        } else {
            if(buyerRepository.findBuyerByEmail(newBuyer.getEmail()).isPresent()) {
                throw new IllegalStateException("Email already in use, please choose different email!");
            }
        }

        if (newBuyer.getFirstName() != null &&
                newBuyer.getFirstName().length() > 0 &&
                !Objects.equals(oldBuyer.getFirstName(), newBuyer.getFirstName())) {
            oldBuyer.setFirstName(newBuyer.getFirstName());
        }

        if (newBuyer.getLastName() != null &&
                newBuyer.getLastName().length() > 0 &&
                !Objects.equals(oldBuyer.getLastName(), newBuyer.getLastName())) {
            oldBuyer.setLastName(newBuyer.getLastName());
        }

        if (newBuyer.getAddress() != null &&
                newBuyer.getAddress().length() > 0 &&
                !Objects.equals(oldBuyer.getAddress(), newBuyer.getAddress())) {
            oldBuyer.setAddress(newBuyer.getAddress());
        }

        if (newBuyer.getPhoneNumber() != null &&
                newBuyer.getPhoneNumber().length() > 0 &&
                !Objects.equals(oldBuyer.getPhoneNumber(), newBuyer.getPhoneNumber())) {
            oldBuyer.setPhoneNumber(newBuyer.getPhoneNumber());
        }

        oldBuyer.setDateBirth(newBuyer.getDateBirth());
    }

    public Buyer getBuyerByEmail(String email) throws BuyerNotFoundException {
        Optional<Buyer> optionalBuyer = buyerRepository.findBuyerByEmail(email);
        if (optionalBuyer.isPresent()) {
            return optionalBuyer.get();
        }
        throw new BuyerNotFoundException("204", "Could not find any buyer with Email :  " + email);
    }
}
