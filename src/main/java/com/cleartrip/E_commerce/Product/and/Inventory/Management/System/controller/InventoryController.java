package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.controller;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.InventoryDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Inventory;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody InventoryDTO inventoryDTO) {
        return ResponseEntity.ok(inventoryService.addInventory(inventoryDTO));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        Optional<Inventory> inventory = inventoryService.getInventoryByProductId(productId);
        return inventory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @PutMapping
    public ResponseEntity<Inventory> updateStock(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.updateStock(inventory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok().build();
    }
}