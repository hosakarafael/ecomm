package com.rafaelhosaka.ecomm.auth;

import com.rafaelhosaka.ecomm.account.UserAccount;
import com.rafaelhosaka.ecomm.exception.PasswordNotCorrectException;
import com.rafaelhosaka.ecomm.exception.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsManager {

    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserService(@Qualifier("custom") ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserRepository.getApplicationUserByUsername(username);
    }

    @Override
    public void createUser(UserDetails userDetails) throws UsernameDuplicatedException{
        if(applicationUserRepository.getApplicationUserByUsername(userDetails.getUsername()).getUserAccount() != null){
            throw new UsernameDuplicatedException("204", "The username "+ userDetails.getUsername() +" already taken");
        }
        applicationUserRepository.createApplicationUser(userDetails);
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return false;
    }

    public void updateUserPassword(String username,String currentPassword, String newPassword) throws PasswordNotCorrectException {
        UserAccount userAccount = applicationUserRepository.getApplicationUserByUsername(username).getUserAccount();
        if(userAccount != null){
            applicationUserRepository.updateApplicationUserPassword(userAccount, currentPassword, newPassword);
        }
    }
}
