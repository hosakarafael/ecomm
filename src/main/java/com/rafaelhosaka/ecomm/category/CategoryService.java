package com.rafaelhosaka.ecomm.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public void save(Category category){
        categoryRepository.save(category);
    }

    public Category findByName(String name){
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        if(!categoryOptional.isPresent()){
            //TODO throw exception
        }
        return categoryOptional.get();
    }
}
