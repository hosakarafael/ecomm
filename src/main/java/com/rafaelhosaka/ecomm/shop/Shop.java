package com.rafaelhosaka.ecomm.shop;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String description;

    private String phoneNumber;

    private String address;

    @OneToOne(mappedBy = "shop")
    Buyer buyer;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public Shop() {
    }

    public Shop(String name, String description, String phoneNumber, String address, Buyer buyer) {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.buyer = buyer;
    }
}
