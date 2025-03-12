package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
