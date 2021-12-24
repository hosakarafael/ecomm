package com.rafaelhosaka.ecomm.user;

import javax.persistence.*;

public enum UserRole {
    ADMIN, BUYER, GUEST;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ROLE_");
        sb.append(this.name());
        return sb.toString();
    }
}
