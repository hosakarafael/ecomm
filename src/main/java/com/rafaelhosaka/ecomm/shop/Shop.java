package com.rafaelhosaka.ecomm.shop;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ToString.Exclude
    @OneToOne(mappedBy = "shop")
    Buyer buyer;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<Product>();

    public Shop(String name, String description, String phoneNumber, String address, Buyer buyer) {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.buyer = buyer;
    }

    public Set<Product> getSortedProductsByName(){
        return this.products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
