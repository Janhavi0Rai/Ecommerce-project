package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Inventory;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);
    
    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId")
    Optional<Inventory> findByProductId(Long productId);
}
