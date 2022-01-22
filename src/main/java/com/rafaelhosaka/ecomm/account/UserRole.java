package com.rafaelhosaka.ecomm.account;

import javax.persistence.*;

public enum UserRole {
    ADMIN, BUYER;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ROLE_");
        sb.append(this.name());
        return sb.toString();
    }
}
