package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.UserLoginDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto.UserRegistrationDTO;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.UserRole;
import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDTO registrationDTO) {
        // Check if email is already registered
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        // Check if username is already taken
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        }

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setAddress(registrationDTO.getAddress());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setRole(registrationDTO.getRole() != null ? registrationDTO.getRole() : UserRole.CUSTOMER);

        return userRepository.save(user);
    }

    public User loginUser(UserLoginDTO loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, String firstName, String lastName, String address, String phoneNumber) {
        User user = getUserById(id);
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }

    public User createAdminUser(UserRegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setAddress(registrationDTO.getAddress());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setRole(UserRole.ADMIN);

        return userRepository.save(user);
    }
}