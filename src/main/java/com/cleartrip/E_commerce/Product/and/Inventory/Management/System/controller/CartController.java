package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.controller;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Cart;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        Cart updatedCart = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        Cart updatedCart = cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> viewCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }
}
