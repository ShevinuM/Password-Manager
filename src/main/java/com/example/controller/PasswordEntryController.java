package com.example.controller;

import com.example.config.AppConfiguration;
import com.example.interfaces.PasswordSaveCallback;
import com.example.storage.PasswordEntry;
import com.example.storage.SerializationPasswordStorage;

/**
 * This controller is responsible for saving a new password which is added
 */
public class PasswordEntryController implements PasswordSaveCallback {

    private final SerializationPasswordStorage passwordStorage;
    private final String locationToSave;

    public PasswordEntryController(SerializationPasswordStorage passwordStorage, AppConfiguration appConfig) {
        this.passwordStorage = passwordStorage;
        this.locationToSave = appConfig.getFileLocation();
    }

    /**
     * Called when the save button is pressed.
     * @param username The username to save.
     * @param password The password to save.
     * @param website The website to save.
     * @param folder The folder to save.
     */
    public void handleSaveButtonPressed(String username, String password, String website, String folder) {
        PasswordEntry passwordObj = new PasswordEntry(username, password, website, folder);
        passwordStorage.addPasswordEntry(passwordObj, this);
    }

    /**
     * Called when the cancel button is pressed.
     */
    public void handleCancelButtonPressed() {
        // TODO: Implement the method to discard the UI and go back to the previous screen.
    }

    /**
     * Called when the reset button is pressed.
     */
    public void handleResetButtonPressed() {
        // TODO: Implement the UI so that all the text fields are cleared out
    }

    @Override
    public void onAddPasswordEntrySuccess() {
        passwordStorage.savePasswords(this, locationToSave);
    }

    @Override
    public void onAddPasswordEntryError(String error) {
        if (error.equals("password_already_exists")){
            // TODO: Implement the UI to show the user that password exists and allow the user to edit the already
            //  existing password or cancel.
        }
    }

    @Override
    public void onPasswordSaveSuccess() {
        // TODO: Implement the UI to show the user that the password was saved successfully.
    }

    @Override
    public void onPasswordSaveError(String errorMessage) {
        // TODO: Implement the UI which displays the error message along with the action to the user
    }

}
