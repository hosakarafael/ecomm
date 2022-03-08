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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@SessionScope
public class Cart {
    //key Product -> Quantity of each product
    private Map<Product,Integer> productsQuantityMap = new HashMap<Product,Integer>();

    public int getProductsQuantities(){
        int totalQuantity = 0;
        if(productsQuantityMap == null){
            return totalQuantity;
        }
        for (Product product : productsQuantityMap.keySet()) {
            totalQuantity += productsQuantityMap.get(product);
        }
        return totalQuantity;
    }

    public String getTotalPrice(){
        float totalPrice = 0;
        for (Product product : this.productsQuantityMap.keySet()) {
            totalPrice += product.getPrice() * this.productsQuantityMap.get(product);
        }
        return  new DecimalFormat("##.00").format(totalPrice);
    }

    public void add(Product newProduct){
        if(productsQuantityMap.containsKey(newProduct)){
            this.productsQuantityMap.put(newProduct, this.productsQuantityMap.get(newProduct) + 1);
        }else{
            this.productsQuantityMap.put(newProduct, 1);
        }
    }

    public void remove(Product removedProduct){
        if(this.productsQuantityMap.containsKey(removedProduct)){
            this.productsQuantityMap.remove(removedProduct);
        }
    }

    public void update(Product product, Integer newQuantity) {
        productsQuantityMap.put(product, newQuantity);
    }
}
