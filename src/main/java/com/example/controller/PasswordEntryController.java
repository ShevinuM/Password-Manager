package com.example.controller;

import com.example.storage.PasswordEntry;

public class PasswordEntryController {

    private PasswordEntry passwordEntry;

    public PasswordEntryController(String username, String password, String website) {
        try {
            passwordEntry = new PasswordEntry(username, password, website);
        } catch (IllegalArgumentException e) {

        }
    }

}
