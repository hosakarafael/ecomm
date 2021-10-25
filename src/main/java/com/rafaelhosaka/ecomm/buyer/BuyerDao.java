package com.rafaelhosaka.ecomm.buyer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BuyerDao extends JpaRepository<Buyer, Long> {

    @Query("SELECT b FROM Buyer b WHERE b.email = ?1")
    Optional<Buyer> findBuyerByEmail(String email);
}
