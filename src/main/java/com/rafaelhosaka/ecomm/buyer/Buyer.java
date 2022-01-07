package com.rafaelhosaka.ecomm.buyer;

import com.rafaelhosaka.ecomm.shop.Shop;
import com.rafaelhosaka.ecomm.user.User;

import javax.persistence.*;
import java.time.LocalDate;

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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "point=" + point +
                '}';
    }

}
