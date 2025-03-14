package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.UserLoginDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.UserRegistrationDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.UserRole;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(UserRegistrationDTO registrationDTO) {
        // Check if email is already registered
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Check if username is already taken
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword()); // Note: In a real application, you should encrypt the password
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setAddress(registrationDTO.getAddress());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setRole(UserRole.CUSTOMER); // Set default role as CUSTOMER

        return userRepository.save(user);
    }

    public User loginUser(UserLoginDTO loginDto) {
        // Find user by email
        Optional<User> userOpt = userRepository.findByEmail(loginDto.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        // Check password (in a real app, would use password encoder)
        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, String firstName, String lastName, 
                         String address, String phoneNumber) {
        User user = getUserById(id);
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}