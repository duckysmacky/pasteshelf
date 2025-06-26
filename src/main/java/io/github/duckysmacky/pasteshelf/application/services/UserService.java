package io.github.duckysmacky.pasteshelf.application.services;

import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.infrastructure.repositories.UserRepository;
import io.github.duckysmacky.pasteshelf.web.error.UserAlreadyExistsException;
import io.github.duckysmacky.pasteshelf.web.error.UserNotFoundException;
import io.github.duckysmacky.pasteshelf.web.util.UserValidator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String rawPassword, String email) {
        if (repository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(String.format("Username '%s' is already taken", username));
        }

        if (repository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(String.format("Email '%s' is already in use", email));
        }

        userValidator.validateUser(username, rawPassword, email);

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, encodedPassword, email);

        return repository.save(user);
    }

    public User updateUser(String username, String newUsername, String newRawPassword, String newEmail) {
        User user = repository.findByUsername(username)
            .orElseThrow(UserNotFoundException::new);

        if (newUsername != null) {
            userValidator.validateUsername(newUsername);
            user.setUsername(newUsername);
        }

        if (newEmail != null) {
            userValidator.validateEmail(newEmail);
            user.setEmail(newEmail);
        }

        if (newRawPassword != null) {
            userValidator.validatePassword(newRawPassword);
            String encodedPassword = passwordEncoder.encode(newRawPassword);
            user.setPassword(encodedPassword);
        }

        return repository.save(user);
    }

    public void deleteUser(String username) {
        User user = repository.findByUsername(username)
            .orElseThrow(UserNotFoundException::new);

        repository.delete(user);
    }


    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username '%s' was not found", username)));

        return new org.springframework.security.core.userdetails.
            User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
