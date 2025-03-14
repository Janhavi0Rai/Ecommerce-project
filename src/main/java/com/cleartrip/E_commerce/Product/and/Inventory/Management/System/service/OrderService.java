package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.*;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.OrderStatus;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    // Add custom exceptions
    public static class InsufficientStockException extends RuntimeException {
        public InsufficientStockException(String message) {
            super(message);
        }
    }

    public static class EmptyCartException extends RuntimeException {
        public EmptyCartException(String message) {
            super(message);
        }
    }

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cannot place order with empty cart");
        }

        validateAndUpdateInventory(cart.getItems());
        Order order = createOrderFromCart(cart, user);
        cartRepository.delete(cart);

        return orderRepository.save(order);
    }

    private void validateAndUpdateInventory(List<CartItem> items) {
        for (CartItem item : items) {
            Inventory inventory = inventoryRepository.findByProduct(item.getProduct())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory not found for product: " + item.getProduct().getName()));

            if (inventory.getQuantity() < item.getQuantity()) {
                throw new InsufficientStockException(
                    "Insufficient stock for product: " + item.getProduct().getName());
            }

            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
            inventoryRepository.save(inventory);
        }
    }

    private Order createOrderFromCart(Cart cart, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(calculateOrderTotal(cart.getItems(), order));
        return order;
    }

    private BigDecimal calculateOrderTotal(List<CartItem> items, Order order) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (CartItem cartItem : items) {
            OrderItem orderItem = createOrderItem(cartItem, order);
            order.getItems().add(orderItem);
            
            BigDecimal itemTotal = cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }
        
        return totalAmount;
    }

    private OrderItem createOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPriceAtTime(cartItem.getProduct().getPrice());
        return orderItem;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
}