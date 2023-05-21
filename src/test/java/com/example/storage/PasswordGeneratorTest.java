package com.example.storage;

import com.example.utilities.PasswordGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {

    @Test
    public void testGeneratePasswordWithAllCriteriaEnabled() {
        try {
            String password = PasswordGenerator.generatePassword(true, true, true,
                    12);
            Assertions.assertEquals(12, password.length());
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGeneratePasswordWithOnlyAlphabets() {
        try {
            String password = PasswordGenerator.generatePassword(false, false,
                    true, 8);
            Assertions.assertEquals(8, password.length());
            Assertions.assertTrue(password.matches("[a-zA-Z]+"));
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGeneratePasswordWithSpecialCharactersAndNumbers() {
        try {
            String password = PasswordGenerator.generatePassword(true, true, false,
                    10);
            Assertions.assertEquals(10, password.length());
            Assertions.assertTrue(password.matches("[!@#$%^&*()-_=+[{]};:'\",<.>/?0-9]+"));
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGeneratePasswordWithOnlyNumbers() {
        try {
            // Generate password with only numbers
            String password = PasswordGenerator.generatePassword(false, true,
                    false, 6);

            // Assert
            Assertions.assertEquals(6, password.length());
            Assertions.assertTrue(password.matches("[0-9]+"));
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGeneratePasswordWithOnlySpecialCharacters() {
        try {
            // Generate password with only special characters
            String password = PasswordGenerator.generatePassword(true, false,
                    false, 8);

            // Assert
            Assertions.assertEquals(8, password.length());
            Assertions.assertTrue(password.matches("[!@#$%^&*()-_=+[{]};:'\\\\\",<.>/?]+"));
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGeneratePasswordWithSpecialCharactersAndLetters() {
        try {
            // Generate password with special characters and letters
            String password = PasswordGenerator.generatePassword(true, false, true,
                    10);

            // Assert
            Assertions.assertEquals(10, password.length());
            Assertions.assertTrue(password.matches("[a-zA-Z!@#$%^&*()-_=+[{]};:'\\\\\",<.>/?]+"));
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGeneratePasswordWithDifferentLengths() {
        try {
            // Generate password with length 4
            String password4 = PasswordGenerator.generatePassword(true, true, true,
                    4);
            Assertions.assertEquals(4, password4.length());

            // Generate password with length 16
            String password16 = PasswordGenerator.generatePassword(true, true,
                    true, 16);
            Assertions.assertEquals(16, password16.length());

            // Generate password with length 20
            String password20 = PasswordGenerator.generatePassword(true, true,
                    true, 20);
            Assertions.assertEquals(20, password20.length());
        } catch (Exception e) {
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGeneratePasswordWithNoCriteriaEnabled() {
        Assertions.assertThrows(Exception.class, () -> PasswordGenerator.generatePassword(false,
                false, false, 6));
    }

}
