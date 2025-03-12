
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.service;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.User;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
