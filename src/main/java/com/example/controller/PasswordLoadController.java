package com.example.controller;

import com.example.interfaces.PasswordLoadCallback;
import com.example.storage.PasswordEntry;
import com.example.storage.SerializationPasswordStorage;

import java.util.List;

/**
 * The controller for loading passwords.
 */
public class PasswordLoadController implements PasswordLoadCallback {

    private final SerializationPasswordStorage passwordStorage = new SerializationPasswordStorage();

    /**
     * Called when the password loading is successful.
     * @param passwordEntries The list of password entries loaded successfully.
     */
    public void onPasswordLoadSuccess(List<PasswordEntry> passwordEntries) {

    }

    /**
     * Called when an error occurs during password loading.
     * @param errorMessage The error message describing the cause of the error.
     */
    @Override
    public void onPasswordLoadError(String errorMessage) {

    }

    /**
     * Loads the password entries from the password storage.
     * @return The password storage object.
     */
    public SerializationPasswordStorage loadPasswordEntries() {
        passwordStorage.loadPasswords(this);
        return passwordStorage;
    }

}
