package io.github.duckysmacky.pasteshelf.application.services;

import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User registerUser(String username, String rawPassword, String email) {
        if (repository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }

        User user = new User(username, rawPassword, email);
        return repository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }
}
