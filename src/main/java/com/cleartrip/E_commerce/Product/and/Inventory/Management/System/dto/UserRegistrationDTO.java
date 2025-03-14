package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.UserRole;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private UserRole role;
}