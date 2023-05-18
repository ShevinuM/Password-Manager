package com.example.controller;

import com.example.interfaces.PasswordSaveCallback;
import com.example.storage.PasswordEntry;
import com.example.storage.SerializationPasswordStorage;

public class PasswordSaveController implements PasswordSaveCallback {

    private final SerializationPasswordStorage passwordStorage;

    public PasswordSaveController(SerializationPasswordStorage passwordStorage) {
        this.passwordStorage = passwordStorage;
    }

    @Override
    public void onPasswordSaveSuccess() {

    }

    @Override
    public void onPasswordSaveError(String errorMessage) {

    }

    /**
     * Creates and stores a password entry in the password storage.
     * @param username The username for the password entry.
     * @param password The password for the password entry.
     * @param website The website for the password entry.
     */
    public void createAndStorePassword(String username, String password, String website) {
        PasswordEntry passwordObj = new PasswordEntry(username, password, website);
        passwordStorage.addPasswordEntry(passwordObj);
        storePasswordEntries();
    }

    /**
     * Stores the password entries in the password storage.
     */
    public void storePasswordEntries() {
        passwordStorage.savePasswords(this);
    }

}
