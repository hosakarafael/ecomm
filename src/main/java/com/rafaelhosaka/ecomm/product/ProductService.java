package com.rafaelhosaka.ecomm.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
