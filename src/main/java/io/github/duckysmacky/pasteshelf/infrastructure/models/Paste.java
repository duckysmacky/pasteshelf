package io.github.duckysmacky.pasteshelf.infrastructure.models;

import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        this.hash = getContentHash(content);
        this.content = content;
    }

    // TODO: add some kind of randomize to hash so same pastes won't get the same hash
    private String getContentHash(String content) {
        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 hashing algorithm is unavailable");
        }

        byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));

        StringBuilder hashString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hashString.append('0');
            hashString.append(hex);
        }

        return hashString.substring(0, 16);
    }

    public void updateContent(String content) {
        this.hash = getContentHash(content);
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
