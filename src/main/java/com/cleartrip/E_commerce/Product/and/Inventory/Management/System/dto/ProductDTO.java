package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;
}