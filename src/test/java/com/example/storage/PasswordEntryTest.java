package com.example.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordEntryTest {

    @Test
    public void testGetUsername() {
        String username = "john_doe";
        String password = "password123";
        String website = "example.com";

        PasswordEntry passwordEntry = new PasswordEntry(username, password, website);

        Assertions.assertEquals(username, passwordEntry.getUsername());
    }

    @Test
    public void testGetPassword() {
        String username = "john_doe";
        String password = "password123";
        String website = "example.com";

        PasswordEntry passwordEntry = new PasswordEntry(username, password, website);

        Assertions.assertEquals(password, passwordEntry.getPassword());
    }

    @Test
    public void testGetWebsite() {
        String username = "john_doe";
        String password = "password123";
        String website = "example.com";

        PasswordEntry passwordEntry = new PasswordEntry(username, password, website);

        Assertions.assertEquals(website, passwordEntry.getWebsite());
    }
}
