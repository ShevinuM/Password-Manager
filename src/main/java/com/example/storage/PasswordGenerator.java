package com.example.storage;

import java.security.SecureRandom;


public class PasswordGenerator {
    private final Boolean hasSpecialCharacters;
    private final Boolean hasNumbers;
    private final Boolean hasAplha;
    private final Integer passwordLength;

    public PasswordGenerator(Boolean hasSpecialCharacters,
                             Boolean hasNumbers, Boolean hasAlpha, Integer passwordLength) {
        this.hasSpecialCharacters = hasSpecialCharacters;
        this.hasNumbers = hasNumbers;
        this.hasAplha = hasAlpha;
        this.passwordLength = passwordLength;
    }

    /**
     * Generates a random password based on the specified criteria.
     * @return  A randomly generated password that meets the specified criteria.
     * @throws Exception if no valid characters are available for password generation
     */
    public String generatePassword() throws Exception {
        String validCharacters = "";
        if (hasAplha) validCharacters += "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (hasNumbers) validCharacters += "0123456789";
        if (hasSpecialCharacters) validCharacters += "!@#$%^&*()-_=+[{]};:'\\\",<.>/?";

        if (validCharacters.isEmpty()) throw new Exception("Valid Characters is Empty");

        StringBuilder password = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = secureRandom.nextInt(validCharacters.length());
            char randomChar = validCharacters.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

}
