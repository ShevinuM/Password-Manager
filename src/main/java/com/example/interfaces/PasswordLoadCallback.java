package com.example.interfaces;

import com.example.storage.PasswordEntry;

import java.util.List;

/**
 * The interface for handling password loading callbacks.
 */
public interface PasswordLoadCallback {
    /**
     * Called when the password loading is successful.
     * @param passwordEntries The list of password entries loaded successfully.
     */
    void onPasswordLoadSuccess(List<PasswordEntry> passwordEntries);

    /**
     * Called when an error occurs during password loading.
     * @param errorMessage The error message describing the cause of the error.
     */
    void onPasswordLoadError(String errorMessage);
}
