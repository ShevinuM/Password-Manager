package com.example.interfaces;

/**
 * A callback interface for handling the result of password save operation
 */
public interface PasswordSaveCallback {
    /**
     Called when the password save operation is successful.
     */
    void onPasswordSaveSuccess();

    /**
     * Called when an error occurs during the password save operation
     * @param errorMessage The error message describing the cause of the error
     */
    void onPasswordSaveError(String errorMessage);

    void onAddPasswordEntrySuccess();

    void onAddPasswordEntryError(String error);
}
