package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Order;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findByUserId(Long userId);
}