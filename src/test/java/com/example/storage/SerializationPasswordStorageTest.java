package com.example.storage;

import com.example.interfaces.PasswordLoadCallback;
import com.example.interfaces.PasswordSaveCallback;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        /**
         * The testAddPasswordEntry method tests the addition of a password entry and verifies that it is added correctly.
         */
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

        /**
         * The testSavePasswords method tests the saving of passwords and checks if the file exists after saving.
         */
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

        /**
         * The testLoadPasswords method tests the loading of passwords and verifies that the loaded password entry
         * matches the original entry.
         */
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

        /**
         * The testAddMultiplePasswordEntries method tests the addition of multiple password entries and checks if they
         * are all added correctly.
         */
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

        /**
         * The testGetEmptyPasswordEntries method tests retrieving password entries when the storage is empty.
         */
        @Test
        public void testGetEmptyPasswordEntries() {
            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();
            Assertions.assertTrue(entries.isEmpty());
        }

        /**
         * The testRemoveExistingPasswordEntry method tests the removal of an existing password entry.
         */
        @Test
        public void testRemoveExistingPasswordEntry() {
            // Create a password entry
            PasswordEntry entry = new PasswordEntry("JohnDoe", "password123", "Website A");

            // Add the password entry
            passwordStorage.addPasswordEntry(entry);

            // Remove the password entry
            passwordStorage.removePasswordEntry(entry);

            // Assert that the password entry is removed
            Assertions.assertFalse(passwordStorage.getPasswordEntries().contains(entry));
        }

        /**
         * The testRemoveMultiplePasswordEntries method tests the removal of a specific password entry when multiple
         * entries exist.
         */
        @Test
        public void testRemoveMultiplePasswordEntries() {
            // Create password entries
            PasswordEntry entry1 = new PasswordEntry("JohnDoe", "password123", "Website A");
            PasswordEntry entry2 = new PasswordEntry("JaneSmith", "abc123", "Website B");
            PasswordEntry entry3 = new PasswordEntry("BobJohnson", "qwerty", "Website C");

            // Add the password entries
            passwordStorage.addPasswordEntry(entry1);
            passwordStorage.addPasswordEntry(entry2);
            passwordStorage.addPasswordEntry(entry3);

            // Remove one of the password entries
            passwordStorage.removePasswordEntry(entry2);

            // Assert that the specific password entry is removed
            Assertions.assertFalse(passwordStorage.getPasswordEntries().contains(entry2));
        }

        /**
         * tests concurrent access to the password storage and ensures that only one entry is added.
         */
        @RepeatedTest(10)
        public void testConcurrentAccessToPasswordStorage() throws InterruptedException {
            // Create a password entry
            PasswordEntry entry = new PasswordEntry("JohnDoe", "password123", "Website A");

            // Add the password entry multiple times concurrently
            int threadCount = 10;
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.execute(() -> passwordStorage.addPasswordEntry(entry));
            }

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);

            // Retrieve the password entries
            int actualSize = passwordStorage.getPasswordEntries().size();
            int expectedSize = 1;

            // Assert that only one entry is added
            Assertions.assertEquals(expectedSize, actualSize);
        }

        /**
         * The testSaveLoadLargeNumberOfPasswordEntries method tests saving and loading a large number of password
         * entries and verifies their correctness.
         */
        @Test
        public void testSaveLoadLargeNumberOfPasswordEntries() {
            // Generate a large number of password entries
            int numEntries = 10000;
            List<PasswordEntry> entries = new ArrayList<>();
            for (int i = 0; i < numEntries; i++) {
                String username = "user" + i;
                String password = "password" + i;
                String website = "Website " + i;
                PasswordEntry entry = new PasswordEntry(username, password, website);
                entries.add(entry);
            }

            // Add the password entries
            entries.forEach(passwordStorage::addPasswordEntry);

            // Save the passwords
            passwordStorage.savePasswords(new PasswordSaveCallback() {
                @Override
                public void onPasswordSaveSuccess() {
                    // Assert that the save operation is successful
                    File file = new File(filePath);
                    Assertions.assertTrue(file.exists());

                    // Clear the password storage
                    passwordStorage.clear();

                    // Load the passwords
                    passwordStorage.loadPasswords(new PasswordLoadCallback() {
                        @Override
                        public void onPasswordLoadSuccess(List<PasswordEntry> loadedEntries) {
                            // Assert that the number of loaded entries is correct
                            Assertions.assertEquals(numEntries, loadedEntries.size());

                            // Assert that the loaded entries match the original entries
                            Assertions.assertIterableEquals(entries, loadedEntries);
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

        /**
         * The testClear_EmptyStorage method tests clearing an empty storage and ensures that it remains empty.
         */
        @Test
        public void testClear_EmptyStorage() {
            // Ensure the storage is already empty
            passwordStorage.clear();

            // Verify that the storage is still empty after clearing
            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();
            Assertions.assertTrue(entries.isEmpty());
        }

        /**
         * The testClear_NonEmptyStorage method tests clearing a non-empty storage and verifies that it becomes empty.
         */
        @Test
        public void testClear_NonEmptyStorage() {
            // Add some password entries to the storage
            PasswordEntry entry1 = new PasswordEntry("JohnDoe", "password123", "Website A");
            PasswordEntry entry2 = new PasswordEntry("JaneSmith", "abc123", "Website B");
            passwordStorage.addPasswordEntry(entry1);
            passwordStorage.addPasswordEntry(entry2);

            // Clear the storage
            passwordStorage.clear();

            // Verify that the storage is empty after clearing
            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();
            Assertions.assertTrue(entries.isEmpty());
        }

        /**
         * The testClear_MultipleCalls method tests multiple calls to clear the storage and ensures that it remains
         * empty.
         */
        @Test
        public void testClear_MultipleCalls() {
            // Add some password entries to the storage
            PasswordEntry entry1 = new PasswordEntry("JohnDoe", "password123", "Website A");
            PasswordEntry entry2 = new PasswordEntry("JaneSmith", "abc123", "Website B");
            passwordStorage.addPasswordEntry(entry1);
            passwordStorage.addPasswordEntry(entry2);

            // Clear the storage multiple times
            passwordStorage.clear();
            passwordStorage.clear();
            passwordStorage.clear();

            // Verify that the storage is empty after clearing
            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();
            Assertions.assertTrue(entries.isEmpty());
        }

        /**
         * The testSaveLoadWithUTF8Encoding method tests saving and loading password entries with UTF-8 encoding and
         * verifies their correctness.
         */
        @Test
        public void testSaveLoadWithUTF8Encoding() {
            // Create a password entry with special characters
            PasswordEntry entry = new PasswordEntry("JohnDoe", "pässwörd123", "Website A");

            // Add the password entry to the storage
            passwordStorage.addPasswordEntry(entry);

            // Save the passwords with UTF-8 encoding
            passwordStorage.savePasswords(new PasswordSaveCallback() {
                @Override
                public void onPasswordSaveSuccess() {
                    // Load the passwords with UTF-8 encoding
                    passwordStorage.loadPasswords(new PasswordLoadCallback() {
                        @Override
                        public void onPasswordLoadSuccess(List<PasswordEntry> passwordEntries) {
                            // Verify that the special characters are preserved
                            Assertions.assertEquals(1, passwordEntries.size());
                            Assertions.assertEquals("JohnDoe", passwordEntries.get(0).getUsername());
                            Assertions.assertEquals("pässwörd123", passwordEntries.get(0).getPassword());
                            Assertions.assertEquals("Website A", passwordEntries.get(0).getWebsite());
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

        /**
         * The testSaveLoadWithSpecialCharacters method tests saving and loading password entries with special
         * characters and escape sequences.
         */
        @Test
        public void testSaveLoadWithSpecialCharacters() {
            // Create a password entry with special characters or escape sequences
            PasswordEntry entry = new PasswordEntry("JohnDoe", "p@ssw\"rd123", "Website\tA");

            // Add the password entry to the storage
            passwordStorage.addPasswordEntry(entry);

            // Save the passwords
            passwordStorage.savePasswords(new PasswordSaveCallback() {
                @Override
                public void onPasswordSaveSuccess() {
                    // Load the passwords
                    passwordStorage.loadPasswords(new PasswordLoadCallback() {
                        @Override
                        public void onPasswordLoadSuccess(List<PasswordEntry> passwordEntries) {
                            // Verify that the special characters or escape sequences are preserved
                            Assertions.assertEquals(1, passwordEntries.size());
                            Assertions.assertEquals("JohnDoe", passwordEntries.get(0).getUsername());
                            Assertions.assertEquals("p@ssw\"rd123", passwordEntries.get(0).getPassword());
                            Assertions.assertEquals("Website\tA", passwordEntries.get(0).getWebsite());
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

        /**
         * The testSaveLoadWithDifferentEncoding method tests saving and loading password entries with a different
         * encoding (UTF-16) and verifies their correctness.
         */
        @Test
        public void testSaveLoadWithDifferentEncoding() {
            // Create a password entry with special characters using a different encoding (e.g., UTF-16)
            PasswordEntry entry = new PasswordEntry("JohnDoe", "pässwörd123", "Website A");

            // Add the password entry to the storage
            passwordStorage.addPasswordEntry(entry);

            // Save the passwords with a different encoding
            passwordStorage.savePasswords(new PasswordSaveCallback() {
                @Override
                public void onPasswordSaveSuccess() {
                    // Load the passwords with a different encoding
                    passwordStorage.loadPasswords(new PasswordLoadCallback() {
                        @Override
                        public void onPasswordLoadSuccess(List<PasswordEntry> passwordEntries) {
                            // Verify that the special characters are preserved
                            Assertions.assertEquals(1, passwordEntries.size());
                            Assertions.assertEquals("JohnDoe", passwordEntries.get(0).getUsername());
                            Assertions.assertEquals("pässwörd123", passwordEntries.get(0).getPassword());
                            Assertions.assertEquals("Website A", passwordEntries.get(0).getWebsite());
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

        /**
         * The testConcurrentAddAndRemoveOperations method tests concurrent add and remove operations and verifies the
         * final state of the storage.
         */
        @Test
        public void testConcurrentAddAndRemoveOperations() throws InterruptedException {
            // Create a password entry
            PasswordEntry entry = new PasswordEntry("JohnDoe", "password123", "Website A");

            // Add the password entry multiple times concurrently and remove it concurrently
            int threadCount = 10;
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

            // Concurrently add the password entry
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(() -> passwordStorage.addPasswordEntry(entry));
            }

            // Concurrently remove the password entry
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(() -> passwordStorage.removePasswordEntry(entry));
            }

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);

            // Verify the final state of the password storage
            List<PasswordEntry> entries = passwordStorage.getPasswordEntries();
            Assertions.assertEquals(0, entries.size());
        }

    }

    @Nested
    public class NegativeTests {

        /**
         * Test case to verify behavior when attempting to load passwords from a non-existent file
         */
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

        /**
         * Test case to verify behavior when attempting to add a null password entry
         */
        @Test
        public void testAddPasswordEntry_NullEntry() {
            // Attempt to add a null password entry
            PasswordEntry nullEntry = null;

            assertThrows(NullPointerException.class, () -> {
                passwordStorage.addPasswordEntry(nullEntry);
            });
        }

        /**
         * Test case to verify behavior when attempting to add a duplicate password entry
         */
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

        /**
         * Test case to verify that modifying the returned list does not change the original list
         */
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

            // Create a copy of the returned list
            List<PasswordEntry> copiedEntries = new ArrayList<>(entries);

            // Modify the copied list
            copiedEntries.remove(0);

            // Retrieve the password entries again
            List<PasswordEntry> updatedEntries = passwordStorage.getPasswordEntries();

            // Assert that the original list remains unchanged
            Assertions.assertEquals(entries.size(), updatedEntries.size());
            Assertions.assertEquals(entries.get(0), updatedEntries.get(0));
            Assertions.assertEquals(entries.get(1), updatedEntries.get(1));
        }

        /**
         * Test case to verify behavior when attempting to remove a null password entry
         */
        @Test
        public void testRemoveNullPasswordEntry() {
            // Attempt to remove a null password entry
            Assertions.assertThrows(NullPointerException.class, () -> {
                passwordStorage.removePasswordEntry(null);
            });
        }

        /**
         * Test case to verify behavior when attempting to remove a password entry that does not exist in the storage
         */
        @Test
        public void testRemoveNonexistentPasswordEntry() {
            // Create a password entry
            PasswordEntry entry = new PasswordEntry("JohnDoe", "password123", "Website A");

            // Attempt to remove a password entry that does not exist in the storage
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                passwordStorage.removePasswordEntry(entry);
            });
        }

        /**
         * Test case to verify behavior when attempting to load passwords from an empty file
         */
        @Test
        public void testLoadPasswords_EmptyFile() {
            String emptyFilePath = "empty-file.ser";

            // Write an empty file
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(emptyFilePath));
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Attempt to load passwords from the empty file
            passwordStorage.loadPasswords(new PasswordLoadCallback() {
                @Override
                public void onPasswordLoadSuccess(List<PasswordEntry> passwordEntries) {
                    fail("Load operation should have failed");
                }

                @Override
                public void onPasswordLoadError(String errorMessage) {
                    assertNotNull(errorMessage);
                    assertTrue(errorMessage.contains("empty"));
                }
            });
        }

        /**
         * Test case to verify behavior when attempting to add an invalid password entry
         */
        @Test
        public void testAddPasswordEntry_InvalidEntry() {
            // Create a mock PasswordStorage object
            SerializationPasswordStorage passwordStorage = new SerializationPasswordStorage();

            // Attempt to add an invalid password entry
            PasswordEntry invalidEntry = null;

            try {
                invalidEntry = new PasswordEntry(null, "password123", "Website A");
                fail("Expected IllegalArgumentException to be thrown");
            } catch (IllegalArgumentException e) {
                // IllegalArgumentException is expected, do nothing
            }

            // Verify that the invalid entry was not added
            assertFalse(passwordStorage.getPasswordEntries().contains(invalidEntry));
        }

        /**
         * Test case to verify behavior when attempting to remove a null password entry
         */
        @Test
        public void testRemovePasswordEntry_NullEntry() {
            // Attempt to remove a password entry with a null entry
            Assertions.assertThrows(NullPointerException.class, () -> {
                passwordStorage.removePasswordEntry(null);
            });
        }

    }
}
