package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.shop.Shop;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.util.Base64;

@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

    public Product() {
    }

    public Product(String name, String description, Float price, Integer stock, Category category, Shop shop) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.shop = shop;
    }

    public String getMainImageAsBase64(){
        return Base64Utils.encodeToString(this.mainImage);
    }


}
