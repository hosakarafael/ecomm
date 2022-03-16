package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.category.Category;
import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import com.rafaelhosaka.ecomm.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    public void update(Long productId,Product newProduct) throws ProductNotFoundException{
        Product oldProduct = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("206", "Buyer with ID " + productId + " does not exist")
        );

        if (newProduct.getName() != null &&
                newProduct.getName().length() > 0 &&
                Objects.equals(oldProduct.getName(), newProduct.getName())) {
            oldProduct.setName(newProduct.getName());
        }

        if (newProduct.getDescription() != null &&
                newProduct.getDescription().length() > 0 &&
                !Objects.equals(oldProduct.getDescription(), newProduct.getDescription())) {
            oldProduct.setDescription(newProduct.getDescription());
        }

        oldProduct.setPrice(newProduct.getPrice());

        oldProduct.setCategory(newProduct.getCategory());

        if(newProduct.getMainImage() != null && newProduct.getMainImage().length > 0 &&
                !Objects.equals(oldProduct.getMainImage(), newProduct.getMainImage())){
            oldProduct.setMainImage(newProduct.getMainImage());
        }
    }
}
