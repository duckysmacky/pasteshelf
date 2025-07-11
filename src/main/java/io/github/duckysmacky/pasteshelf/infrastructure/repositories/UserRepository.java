package io.github.duckysmacky.pasteshelf.infrastructure.repositories;

import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
