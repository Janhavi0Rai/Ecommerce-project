package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Cart;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.CartItem;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Inventory;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.CartRepository;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.ProductRepository;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.UserRepository;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Transactional
    public Cart addToCart(Long userId, Long productId, Integer quantity) {
        if (userId == null || productId == null || quantity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID, Product ID, and quantity are required");
        }

        if (quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + productId));

        // Check inventory
        Inventory inventory = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found for product: " + product.getName()));
        
        if (inventory.getQuantity() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Insufficient stock. Available: " + inventory.getQuantity() + " for product: " + product.getName());
        }

        try {
            Cart cart = cartRepository.findByUser(user)
                    .orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setUser(user);
                        return newCart;
                    });

            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseGet(() -> {
                        CartItem newItem = new CartItem();
                        newItem.setCart(cart);
                        newItem.setProduct(product);
                        cart.getItems().add(newItem);
                        return newItem;
                    });

            cartItem.setQuantity(quantity);
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error adding product to cart: " + e.getMessage());
        }
    }

    @Transactional
    public Cart removeFromCart(Long userId, Long productId) {
        if (userId == null || productId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID and Product ID are required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found for user with ID: " + userId));

        boolean removed = cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Product with ID: " + productId + " not found in cart");
        }

        try {
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error removing product from cart: " + e.getMessage());
        }
    }

    public Cart getCartByUserId(Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found for user with ID: " + userId));
    }
}
