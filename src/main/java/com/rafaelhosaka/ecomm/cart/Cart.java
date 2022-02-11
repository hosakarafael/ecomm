package com.rafaelhosaka.ecomm.cart;

import com.rafaelhosaka.ecomm.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@SessionScope
public class Cart {
    private List<Product> products = new ArrayList<>();

    public int getProductsQuantities(){
        if(products == null){
            return 0;
        }
        return products.size();
    }
}
