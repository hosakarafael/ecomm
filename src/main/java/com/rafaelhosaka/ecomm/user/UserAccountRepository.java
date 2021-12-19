package com.rafaelhosaka.ecomm.user;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Query("SELECT u FROM UserAccount u WHERE u.username = ?1")
    UserAccount findByUsername(String username);
}
