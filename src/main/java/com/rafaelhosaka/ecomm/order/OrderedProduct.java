package com.rafaelhosaka.ecomm.order;

import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.product.Product;
import com.rafaelhosaka.ecomm.shop.Shop;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String name;

    private String description;

    private Float price;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

    public String getMainImageAsBase64() {
        final String NO_IMAGE_PATH = "/img/no-image.jpeg";

        if (this.image != null){
            StringBuilder header = new StringBuilder("data:image/jpeg;base64,");
            header.append(Base64Utils.encodeToString(this.image));
            return header.toString();
        }
        return NO_IMAGE_PATH;
    }

    public String getFormattedPrice(){
        return new DecimalFormat("##.00").format(this.price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedProduct orderedProduct = (OrderedProduct) o;

        return id.equals(orderedProduct.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
