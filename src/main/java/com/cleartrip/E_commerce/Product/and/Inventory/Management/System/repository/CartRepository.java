package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Cart;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}