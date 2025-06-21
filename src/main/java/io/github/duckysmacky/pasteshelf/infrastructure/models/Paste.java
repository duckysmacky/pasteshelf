package io.github.duckysmacky.pasteshelf.infrastructure.models;

import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
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
    @Column(nullable = false, unique = true, length = 16)
    private String hash;
    @Column(nullable = false)
    private String content;

    public Paste() {

    }

    public Paste(User owner, String content) {
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
        this.hash = getContentHash(content);
        this.content = content;
    }

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

    public Map<String, String> asResponseBody() {
        return Map.of(
            "owner", owner.getUsername(),
            "hash", hash,
            "content", content
        );
    }

    public void updateContent(String content) {
        this.hash = getContentHash(content);
        this.content = content;
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
