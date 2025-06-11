package io.github.duckysmacky.pasteshelf.repositories;

import io.github.duckysmacky.pasteshelf.models.Paste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasteRepository extends JpaRepository<Paste, UUID> {
    Optional<Paste> findByHash(String hash);
}
