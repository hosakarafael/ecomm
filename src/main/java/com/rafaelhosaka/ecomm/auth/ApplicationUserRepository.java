package com.rafaelhosaka.ecomm.auth;

import com.rafaelhosaka.ecomm.account.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository {
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
    ApplicationUser getApplicationUserByUsername(String username);
    void createApplicationUser(UserDetails userDetails);
    void updateApplicationUserPassword(UserAccount userAccount, String currentPassword, String newPassword);
}
