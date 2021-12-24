package com.rafaelhosaka.ecomm.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ApplicationUserRepository {
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
    ApplicationUser getApplicationUserByUsername(String username);
    void createApplicationUser(UserDetails userDetails);
}
