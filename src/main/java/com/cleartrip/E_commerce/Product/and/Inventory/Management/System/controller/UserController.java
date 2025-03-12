
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.controller;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }
}

