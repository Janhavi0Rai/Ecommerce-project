
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Inventory;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);
    Optional<Inventory> findByProductId(Long productId);
}
