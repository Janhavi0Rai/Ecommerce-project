package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.*;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.OrderStatus;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order placeOrder(Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "Cart not found for user with ID: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        try {
            // Validate and update inventory
            for (CartItem item : cart.getItems()) {
                Inventory inventory = inventoryRepository.findByProduct(item.getProduct())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                            "Inventory not found for product: " + item.getProduct().getName()));

                if (inventory.getQuantity() < item.getQuantity()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                        "Insufficient stock for product: " + item.getProduct().getName() + 
                        ". Available: " + inventory.getQuantity() + 
                        ", Requested: " + item.getQuantity());
                }

                // Update inventory
                inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                inventoryRepository.save(inventory);
            }

            // Create order
            Order order = createOrderFromCart(cart, user);
            order = orderRepository.save(order);

            // Clear cart after successful order creation
            cartRepository.delete(cart);

            return order;
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error processing order: " + e.getMessage());
        }
    }

    public Order getOrderById(Long orderId) {
        if (orderId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ID is required");
        }

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "Order not found with ID: " + orderId));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "User not found with ID: " + userId));

        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    private Order createOrderFromCart(Cart cart, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        // Copy items from cart to order
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtTime(cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);

            // Calculate item total and add to order total
            BigDecimal itemTotal = cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setTotalAmount(totalAmount);
        return order;
    }
}