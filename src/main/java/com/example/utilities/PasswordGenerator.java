package com.example.utilities;

import java.security.SecureRandom;

public class PasswordGenerator {

    /**
     * Generates a random password based on the specified criteria.
     *
     * @param hasSpecialCharacters Whether to include special characters in the password.
     * @param hasNumbers           Whether to include numbers in the password.
     * @param hasAlpha             Whether to include alphabets in the password.
     * @param passwordLength       The desired length of the password.
     * @return A randomly generated password that meets the specified criteria.
     * @throws Exception if no valid characters are available for password generation.
     */
    public static String generatePassword(Boolean hasSpecialCharacters,
                                   Boolean hasNumbers, Boolean hasAlpha, Integer passwordLength) throws Exception {
        String validCharacters = "";
        if (hasAlpha) validCharacters += "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
