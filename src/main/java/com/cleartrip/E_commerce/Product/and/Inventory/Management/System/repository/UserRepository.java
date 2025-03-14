
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}