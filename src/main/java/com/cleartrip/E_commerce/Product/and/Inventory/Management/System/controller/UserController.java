package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.controller;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.UserLoginDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.UserRegistrationDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        User registeredUser = userService.registerUser(registrationDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserLoginDTO loginDTO) {
        User loggedInUser = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loggedInUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, 
                                         @RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam String address,
                                         @RequestParam String phoneNumber) {
        User updatedUser = userService.updateUser(id, firstName, lastName, address, phoneNumber);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}