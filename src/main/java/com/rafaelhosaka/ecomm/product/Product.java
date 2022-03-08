package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.shop.Shop;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    @Column(columnDefinition="BOOLEAN DEFAULT true")
    private boolean isEnabled;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] mainImage;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public String getMainImageAsBase64() {
        final String NO_IMAGE_PATH = "/img/no-image.jpeg";

        if (this.mainImage != null){
            StringBuilder header = new StringBuilder("data:image/jpeg;base64,");
            header.append(Base64Utils.encodeToString(this.mainImage));
            return header.toString();
        }
        return NO_IMAGE_PATH;
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

    public String getFormattedPrice(){
        return new DecimalFormat("##.00").format(this.price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
