package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.InventoryDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Inventory;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.InventoryRepository;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public Optional<Inventory> getInventoryByProductId(Long productId) {
        if (productId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID cannot be null");
        }
        return inventoryRepository.findByProductId(productId);
    }

    @Transactional
    public Inventory addInventory(InventoryDTO inventoryDTO) {
        if (inventoryDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inventory data cannot be null");
        }
        if (inventoryDTO.getProductId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID cannot be null");
        }
        if (inventoryDTO.getQuantity() == null || inventoryDTO.getQuantity() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be a non-negative number");
        }
        if (inventoryDTO.getMinimumQuantity() != null && inventoryDTO.getMinimumQuantity() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum quantity cannot be negative");
        }
        if (inventoryDTO.getMaximumQuantity() != null && inventoryDTO.getMaximumQuantity() < inventoryDTO.getMinimumQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximum quantity must be greater than minimum quantity");
        }

        Product product = productRepository.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + inventoryDTO.getProductId()));

        Optional<Inventory> existingInventory = inventoryRepository.findByProduct(product);
        if (existingInventory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Inventory already exists for product with ID: " + inventoryDTO.getProductId());
        }

        try {
            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(inventoryDTO.getQuantity());
            inventory.setMinimumQuantity(inventoryDTO.getMinimumQuantity());
            inventory.setMaximumQuantity(inventoryDTO.getMaximumQuantity());

            return inventoryRepository.save(inventory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating inventory: " + e.getMessage());
        }
    }

    @Transactional
    public Inventory updateStock(Inventory inventory) {
        if (inventory == null || inventory.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid inventory data");
        }

        Inventory existingInventory = inventoryRepository.findById(inventory.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with ID: " + inventory.getId()));

        if (inventory.getQuantity() != null && inventory.getQuantity() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity cannot be negative");
        }

        if (inventory.getMinimumQuantity() != null) {
            existingInventory.setMinimumQuantity(inventory.getMinimumQuantity());
        }
        if (inventory.getMaximumQuantity() != null) {
            existingInventory.setMaximumQuantity(inventory.getMaximumQuantity());
        }
        if (inventory.getQuantity() != null) {
            existingInventory.setQuantity(inventory.getQuantity());
        }

        try {
            return inventoryRepository.save(existingInventory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating inventory: " + e.getMessage());
        }
    }

    public List<Inventory> getAllInventory() {
        try {
            return inventoryRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving inventory list: " + e.getMessage());
        }
    }

    public void deleteInventory(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inventory ID cannot be null");
        }

        if (!inventoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with ID: " + id);
        }

        try {
            inventoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting inventory: " + e.getMessage());
        }
    }
}