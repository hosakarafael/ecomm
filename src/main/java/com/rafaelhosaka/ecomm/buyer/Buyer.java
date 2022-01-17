package com.rafaelhosaka.ecomm.buyer;

import com.rafaelhosaka.ecomm.shop.Shop;
import com.rafaelhosaka.ecomm.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "buyer")
public class Buyer extends User {
    private Integer point = 0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    public Buyer() {

    }

    public Buyer( String firstName, String lastName, String address, String phoneNumber, LocalDate dateBirth, String email, String password) {
        super(firstName, lastName, address, phoneNumber, dateBirth, email, password);
    }

}
