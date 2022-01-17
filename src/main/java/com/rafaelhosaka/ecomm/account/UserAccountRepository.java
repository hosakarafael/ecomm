package com.rafaelhosaka.ecomm.account;

import com.rafaelhosaka.ecomm.account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Query("SELECT u FROM UserAccount u WHERE u.username = ?1")
    UserAccount findByUsername(String username);
}
