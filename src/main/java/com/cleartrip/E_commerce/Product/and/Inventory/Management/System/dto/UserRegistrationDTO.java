
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String role; // "CUSTOMER" or "ADMIN"
}