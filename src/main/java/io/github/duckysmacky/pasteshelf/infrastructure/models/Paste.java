package io.github.duckysmacky.pasteshelf.infrastructure.models;

import io.github.duckysmacky.pasteshelf.core.security.Hasher;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pastes")
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(nullable = false, unique = true, length = 16)
    private String hash;
    @Column(nullable = false)
    private String content;

    public Paste() {

    }

    public Paste(User owner, String content) {
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.hash = generateHash(owner.getUsername(), content, LocalDateTime.now());
        this.content = content;
    }

    /// Generates a unique hash-id based on owner's username, content and creation time to make it as unique as
    /// possible
    private String generateHash(String ownerUsername, String content, LocalDateTime createdAt) {
        String data = createdAt.toString() + ownerUsername + content;
        Hasher hasher = Hasher.newSha256();
        return hasher.hash(data, 16);
    }

    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User getOwner() {
        return owner;
    }

    public String getHash() {
        return hash;
    }

    public String getContent() {
        return content;
    }
}
