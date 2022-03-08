package com.rafaelhosaka.ecomm.product;

import com.rafaelhosaka.ecomm.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p where p.category.id = ?1")
    List<Product> findByCategoryId(Long id);

    @Query("select p from Product p where p.category.id = ?1 and p.isEnabled = true")
    List<Product> findEnabledByCategoryId(Long id);

    @Query("select p from Product p where upper(p.name) like upper(concat('%', ?1, '%')) and p.category.id = ?2")
    List<Product> findByNameAndCategoryId(String name, Long id);

    @Query("select p from Product p where upper(p.name) like upper(concat('%', ?1, '%')) and p.category.id = ?2 and p.isEnabled = true")
    List<Product> findEnabledByNameAndCategoryId(String name, Long id);

    @Query("select p from Product p where upper(p.name) = upper(?1)")
    List<Product> findByName(String name);

    @Query("select p from Product p where upper(p.name) like upper(concat('%', ?1, '%')) and p.isEnabled = true")
    List<Product> findEnabledByName(String name);

    @Query("select p from Product p where p.isEnabled = true")
    List<Product> findAllEnabled();

    @Query("select p from Product p where p.id = ?1 and p.isEnabled = true")
    Optional<Product> findEnabledById(Long id);



}
