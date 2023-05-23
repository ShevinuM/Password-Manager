package com.example.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * This class is responsible for generating salt and hashing passwords.
 * It uses PBKDF2 with Hmac SHA256 as the hashing algorithm.
 */
public class PasswordHandler {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    /**
     * Generates a random salt.
     * This method uses the SecureRandom class to generate a random salt of length SALT_LENGTH.
     *
     * @return A byte array containing the generated salt.
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Hashes a password with a provided salt using PBKDF2WithHmacSHA256.
     * This method first creates a PBEKeySpec with the password, salt, ITERATIONS, and KEY_LENGTH.
     * Then it creates a SecretKeyFactory instance for the PBKDF2WithHmacSHA256 algorithm, and generates a SecretKey
     * from the PBEKeySpec.
     * The SecretKey is then encoded to a byte array, which is encoded to a Base64 String and returned.
     *
     * @param password The password to hash, provided as a char array. char array is used instead of string for enhanced
     *                 security. String is immutable, so it cannot be cleared from memory after use. With this approach
     *                 we could clear the char array from memory after use.
     * @param salt The salt to use for hashing, provided as a byte array.
     * @return A Base64-encoded String representing the hashed password.
     * @throws NoSuchAlgorithmException If the PBKDF2WithHmacSHA256 algorithm is not available.
     * @throws InvalidKeySpecException If the generated PBEKeySpec is invalid.
     */
    public static String hashPassword(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

}
