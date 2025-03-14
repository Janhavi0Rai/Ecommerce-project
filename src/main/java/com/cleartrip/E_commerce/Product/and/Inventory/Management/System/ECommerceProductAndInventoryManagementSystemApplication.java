package com.cleartrip.E_commerce.Product.and.Inventory.Management.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class ECommerceProductAndInventoryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceProductAndInventoryManagementSystemApplication.class, args);
	}

}
