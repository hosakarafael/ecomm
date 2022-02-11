package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Product findById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()){
            //TODO throw exception
        }
        return optionalProduct.get();
    }

    public List<Product> findByCategoryId(Long categoryId){
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> findByNameAndCategoryId(String name, Long categoryId){
        return productRepository.findByNameAndCategoryId(name,categoryId);
    }
}
