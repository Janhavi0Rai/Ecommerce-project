
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Order;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findByUserId(Long userId);
}