package io.github.duckysmacky.pasteshelf.application.services;

import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.infrastructure.repositories.UserRepository;
import io.github.duckysmacky.pasteshelf.web.error.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String rawPassword, String email) {
        if (repository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(String.format("Username '%s' is already taken", username));
        }

        if (repository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(String.format("Email '%s' is already in use", email));
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, encodedPassword, email);

        return repository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}
