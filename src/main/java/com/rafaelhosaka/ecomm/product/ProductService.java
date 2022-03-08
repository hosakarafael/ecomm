package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<Product> findAllEnabled(){
        return productRepository.findAllEnabled();
    }

    public Product findById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()){
            //TODO throw exception
        }
        return optionalProduct.get();
    }

    public Product findEnabledById(Long id){
        Optional<Product> optionalProduct = productRepository.findEnabledById(id);
        if(!optionalProduct.isPresent()){
            //TODO throw exception
        }
        return optionalProduct.get();
    }

    @Transactional
    public List<Product> findByCategoryId(Long categoryId){
        return productRepository.findByCategoryId(categoryId);
    }

    @Transactional
    public List<Product> findEnabledByCategoryId(Long categoryId){
        return productRepository.findEnabledByCategoryId(categoryId);
    }

    public List<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> findEnabledByName(String name){
        return productRepository.findEnabledByName(name);
    }

    public List<Product> findByNameAndCategoryId(String name, Long categoryId){
        return productRepository.findByNameAndCategoryId(name,categoryId);
    }

    public List<Product> findEnabledByNameAndCategoryId(String name, Long categoryId){
        return productRepository.findEnabledByNameAndCategoryId(name,categoryId);
    }
}
