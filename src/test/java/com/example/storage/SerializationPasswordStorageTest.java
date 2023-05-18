package com.example.storage;

import com.example.interfaces.PasswordLoadCallback;
import com.example.interfaces.PasswordSaveCallback;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationPasswordStorageTest {

    private SerializationPasswordStorage passwordStorage;
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

    @Nested
    public class PositiveTests {

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
                    fail("Failed to save passwords: " + errorMessage);
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
                            fail("Failed to load passwords: " + errorMessage);
                        }
                    });
                }

                @Override
                public void onPasswordSaveError(String errorMessage) {
                    fail("Failed to save passwords: " + errorMessage);
                }
            });
        }

        @Test
        public void testAddMultiplePasswordEntries() {
            PasswordEntry entry1 = new PasswordEntry("JohnDoe", "password123", "Website A");
            PasswordEntry entry2 = new PasswordEntry("JaneSmith", "abc123", "Website B");
            PasswordEntry entry3 = new PasswordEntry("BobJohnson", "qwerty", "Website C");

            passwordStorage.addPasswordEntry(entry1);
            passwordStorage.addPasswordEntry(entry2);
            passwordStorage.addPasswordEntry(entry3);

            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();

            Assertions.assertEquals(3, entries.size());
            Assertions.assertTrue(entries.contains(entry1));
            Assertions.assertTrue(entries.contains(entry2));
            Assertions.assertTrue(entries.contains(entry3));
        }

        @Test
        public void testGetEmptyPasswordEntries() {
            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();
            Assertions.assertTrue(entries.isEmpty());
        }

    }

    @Nested
    public class NegativeTests {

        @Test
        public void testLoadPasswords_FileNotFound() {
            // Attempt to load passwords from a non-existent file
            String invalidFilePath = "nonexistent-file.ser";

            passwordStorage.loadPasswords(new PasswordLoadCallback() {
                @Override
                public void onPasswordLoadSuccess(List<PasswordEntry> passwordEntries) {
                    fail("Load operation should have failed");
                }

                @Override
                public void onPasswordLoadError(String errorMessage) {
                    assertNotNull(errorMessage);
                }
            });
        }

        @Test
        public void testAddPasswordEntry_NullEntry() {
            // Attempt to add a null password entry
            PasswordEntry nullEntry = null;

            assertThrows(NullPointerException.class, () -> {
                passwordStorage.addPasswordEntry(nullEntry);
            });
        }

        @Test
        public void testAddDuplicatePasswordEntry() {
            // Create a password entry
            PasswordEntry entry = new PasswordEntry("JohnDoe", "password123", "Website A");

            // Add the password entry
            passwordStorage.addPasswordEntry(entry);

            // Attempt to add the same password entry again
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                passwordStorage.addPasswordEntry(entry);
            });
        }

        @Test
        public void testModifyReturnedList_OriginalListUnchanged() {
            // Create password entries
            PasswordEntry entry1 = new PasswordEntry("JohnDoe", "password123", "Website A");
            PasswordEntry entry2 = new PasswordEntry("JaneSmith", "abc123", "Website B");

            // Add the password entries
            passwordStorage.addPasswordEntry(entry1);
            passwordStorage.addPasswordEntry(entry2);

            // Retrieve the password entries
            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();

            // Modify the returned list
            entries.remove(0);

            // Retrieve the password entries again using getPasswordEntries()
            List<PasswordEntry> updatedEntries = passwordStorage.getPasswordEntries();

            // Assert that the original list remains unchanged
            Assertions.assertEquals(1, updatedEntries.size());
            Assertions.assertEquals(entry1, updatedEntries.get(0));

            // Retrieve the password entries again using getPasswordEntries()
            List<PasswordEntry> originalEntries = passwordStorage.getPasswordEntries();

            // Assert that the original list remains unchanged
            Assertions.assertEquals(2, originalEntries.size());
            Assertions.assertEquals(entry1, originalEntries.get(0));
            Assertions.assertEquals(entry2, originalEntries.get(1));
        }

    }

}
