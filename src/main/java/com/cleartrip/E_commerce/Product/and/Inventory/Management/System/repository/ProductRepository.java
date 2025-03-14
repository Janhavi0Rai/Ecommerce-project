package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryIgnoreCase(String category);
    List<Product> findByCategory(String category);
}
