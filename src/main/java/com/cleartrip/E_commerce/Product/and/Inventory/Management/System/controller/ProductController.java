package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.controller;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.ProductDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO) {
        Product newProduct = productService.addProduct(productDTO);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.searchByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }
}