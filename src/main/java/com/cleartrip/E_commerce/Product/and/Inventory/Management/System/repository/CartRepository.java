package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Cart;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}