package io.github.duckysmacky.pasteshelf.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/// Class which handles hashing operations. Provides basic hashing methods to easily get a hash from a `String` or
/// `byte[]`. For now only supports the `Sha-256` hashing algorithm
public class Hasher {
    private final MessageDigest digest;

    /// Private constructor to prevent invalid algorithm creation based on an incorrect name
    private Hasher(String algorithm) {
        try {
            this.digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("The " + algorithm + " hashing algorithm not found");
        }
    }

    /// Creates a new `Hasher` instance with the `Sha-256` hashing algorithm
    public static Hasher newSha256() {
        return new Hasher("SHA-256");
    }

    /// Hashes the provided string and returns a hash of a specified `length`
    public String hash(String data, int length) {
        return hash(data.getBytes(), length);
    }

    /// Hashes the provided bytes and returns a hash of a specified `length`
    public String hash(byte[] data, int length) {
        byte[] hash = digest.digest(data);

        StringBuilder hashString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hashString.append('0');
            hashString.append(hex);
        }

        return hashString.substring(0, length);
    }
}
