package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.shop.Shop;
import lombok.*;
import org.springframework.util.Base64Utils;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "shop")
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String description;

    private Float price;

    private Integer stock;

    @Lob
    private byte[] mainImage;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public String getMainImageAsBase64() {
        return Base64Utils.encodeToString(this.mainImage);
    }

    public Product(String name, String description, Float price, Integer stock, Category category, Shop shop) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.mainImage = mainImage;
        this.category = category;
        this.shop = shop;
    }
}
