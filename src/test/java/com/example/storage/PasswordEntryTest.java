package com.example.storage;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEntryTest {

    @Test
    public void testGetters() {
        // Arrange
        String username = "john_doe";
        String password = "pa55w0rd";
        String website = "www.example.com";

        // Act
        PasswordEntry entry = new PasswordEntry(username, password, website);

        // Assert
        assertEquals(username, entry.getUsername());
        assertEquals(password, entry.getPassword());
        assertEquals(website, entry.getWebsite());
    }

    @Test
    public void testEquality() {
        // Arrange
        PasswordEntry entry1 = new PasswordEntry("john_doe", "pa55w0rd", "www.example.com");
        PasswordEntry entry2 = new PasswordEntry("john_doe", "pa55w0rd", "www.example.com");
        PasswordEntry entry3 = new PasswordEntry("jane_smith", "pa55w0rd", "www.example.com");

        // Assert
        assertEquals(entry1, entry1);  // Object is equal to itself
        assertNotEquals(entry1, entry3);  // Objects with different username are not equal
        assertNotEquals(entry1, null);  // Object is not equal to null
    }

    @Test
    public void testHashCodeInequality() {
        // Arrange
        PasswordEntry entry1 = new PasswordEntry("john_doe", "pa55w0rd", "www.example.com");
        PasswordEntry entry3 = new PasswordEntry("jane_smith", "pa55w0rd", "www.example.com");

        // Assert
        assertNotEquals(entry1.hashCode(), entry3.hashCode());  // Objects with different username have different hash codes
    }

    @Test
    public void testSerializationDeserialization() {
        // Arrange
        PasswordEntry entry = new PasswordEntry("john_doe", "pa55w0rd", "www.example.com");
        String filePath = "passwordEntry.ser";

        try {
            // Serialize the object
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
            outputStream.writeObject(entry);
            outputStream.close();

            // Deserialize the object
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath));
            PasswordEntry deserializedEntry = (PasswordEntry) inputStream.readObject();
            inputStream.close();

            // Assert
            assertEquals(entry, deserializedEntry);
        } catch (IOException | ClassNotFoundException e) {
            fail("Exception occurred: " + e.getMessage());
        } finally {
            // Clean up the serialized file
            File file = new File(filePath);
            file.delete();
        }
    }

}
