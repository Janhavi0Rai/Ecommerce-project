
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByNameContaining(String name);
}
