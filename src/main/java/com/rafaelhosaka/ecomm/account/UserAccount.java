package com.rafaelhosaka.ecomm.account;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean active;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role")
    @Column(name = "userRoles", nullable = false)
    @Enumerated(EnumType.STRING)
    Collection<UserRole> userRoles;

    public UserAccount() {
    }

    public UserAccount(Long id, String username, String password, boolean active, List<UserRole> userRoles) {
        id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.userRoles = userRoles;
    }

    public UserAccount(String username, String password, boolean active, List<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.userRoles = userRoles;
    }
}
