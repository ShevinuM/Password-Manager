package com.example.controller;

import com.example.interfaces.PasswordSaveCallback;
import com.example.storage.Folder;
import com.example.storage.PasswordEntry;
import com.example.storage.SerializationPasswordStorage;

/**
 * This controller is responsible for saving a new password which is added
 */
public class PasswordSaveController implements PasswordSaveCallback {

    private final SerializationPasswordStorage passwordStorage;

    public PasswordSaveController(SerializationPasswordStorage passwordStorage) {
        this.passwordStorage = passwordStorage;
    }

    public void handleSaveButtonPressed(String username, String password, String website, String folder) {
        PasswordEntry passwordObj = new PasswordEntry(username, password, website, folder);
        if (!passwordStorage.passwordAlreadyExists(passwordObj)) {
            passwordStorage.addPasswordEntry(passwordObj);
        } else {
            
        }
    }

    @Override
    public void onAddPasswordEntrySuccess() {

    }

    @Override
    public void onAddPasswordEntryError(String error) {

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
