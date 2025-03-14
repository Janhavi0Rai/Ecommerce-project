package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryIgnoreCase(String category);
}
