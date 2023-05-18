package com.example.storage;

import com.example.interfaces.PasswordLoadCallback;
import com.example.interfaces.PasswordSaveCallback;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SerializationPasswordStorageTest {

    private SerializationPasswordStorage passwordStorage;

    @Nested
    public class PositiveTests {
        private String filePath;

        @BeforeEach
        public void setUp() {
            passwordStorage = new SerializationPasswordStorage();
            filePath = System.getProperty("user.home") + File.separator + "passwords.ser";
        }

        @AfterEach
        public void tearDown() throws Exception {
            // Delete the file after each test case finishes
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }

        @Test
        public void testAddPasswordEntry() {
            // Create a password entry
            PasswordEntry entry = new PasswordEntry("JohnDoe", "password123", "Website A");

            // Add the password entry
            passwordStorage.addPasswordEntry(entry);

            // Retrieve the password entries
            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();

            // Assert that the password entry is added
            Assertions.assertEquals(1, entries.size());
            Assertions.assertEquals(entry, entries.get(0));
        }

        @Test
        public void testSavePasswords() {
            // Create password entries
            PasswordEntry entry1 = new PasswordEntry("JohnDoe", "password123", "Website A");
            PasswordEntry entry2 = new PasswordEntry("JaneSmith", "abc123", "Website B");

            // Add the password entries
            passwordStorage.addPasswordEntry(entry1);
            passwordStorage.addPasswordEntry(entry2);

            // Save the passwords
            passwordStorage.savePasswords(new PasswordSaveCallback() {
                @Override
                public void onPasswordSaveSuccess() {
                    // Assert that the save operation is successful
                    File file = new File(filePath);
                    Assertions.assertTrue(file.exists());
                }

                @Override
                public void onPasswordSaveError(String errorMessage) {
                    Assertions.fail("Failed to save passwords: " + errorMessage);
                }
            });
        }

        @Test
        public void testLoadPasswords() {
            // Create a password entry
            PasswordEntry entry = new PasswordEntry("JohnDoe", "password123", "Website A");

            // Add the password entry
            passwordStorage.addPasswordEntry(entry);

            // Save the passwords
            passwordStorage.savePasswords(new PasswordSaveCallback() {
                @Override
                public void onPasswordSaveSuccess() {
                    // Load the passwords
                    passwordStorage.loadPasswords(new PasswordLoadCallback() {
                        @Override
                        public void onPasswordLoadSuccess(List<PasswordEntry> passwordEntries) {
                            // Assert that the password entry is loaded
                            Assertions.assertEquals(1, passwordEntries.size());
                            Assertions.assertEquals(entry, passwordEntries.get(0));
                        }

                        @Override
                        public void onPasswordLoadError(String errorMessage) {
                            Assertions.fail("Failed to load passwords: " + errorMessage);
                        }
                    });
                }

                @Override
                public void onPasswordSaveError(String errorMessage) {
                    Assertions.fail("Failed to save passwords: " + errorMessage);
                }
            });
        }

    }

}
