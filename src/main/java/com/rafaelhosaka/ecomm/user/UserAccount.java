package com.rafaelhosaka.ecomm.user;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "Id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", userRoles=" + userRoles +
                '}';
    }
}
