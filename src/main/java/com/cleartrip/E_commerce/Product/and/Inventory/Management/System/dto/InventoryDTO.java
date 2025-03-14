package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto;

import lombok.Data;

@Data
public class InventoryDTO {
    private Long productId;
    private Integer quantity;
    private Integer minimumQuantity;
    private Integer maximumQuantity;
} 