package com.example.storage;

import com.example.interfaces.PasswordLoadCallback;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordStorage {

    private List<PasswordEntry> passwordEntries;

    public PasswordStorage() {
        passwordEntries = new ArrayList<>();
    }

    /**
     * Adds a PasswordEntry to the password storage.
     *
     * @param passwordEntry The PasswordEntry to be added.
     */
    public void addPasswordEntry(PasswordEntry passwordEntry) {
        passwordEntries.add(passwordEntry);
    }

    /**
     * Retrieves the list of password entries.
     *
     * @return The list of password entries.
     */
    public List<PasswordEntry> getPasswordEntries() {
        return passwordEntries;
    }

    /**
     * Saves the password entries to a file in the user's home directory using object serialization.
     * The file will be saved with a predetermined name.
     */
    public void savePasswords() {
        String userHomeDirectory = System.getProperty("user.home");
        String filePath = userHomeDirectory + File.separator + "passwords.ser";
        File file = new File(filePath)

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            if (file.exists()) outputStream.reset(); // File already exists, overwrite its contents
            outputStream.writeObject(passwordEntries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the password entries from a file in the user's home directory using object deserialization.
     * The file should be saved with the predetermined name.
     *
     * @param callback The callback interface to handle the password load result.
     *                 The callback methods will be invoked to notify the caller about the success or failure of the operation.
     */
    public void loadPasswords(PasswordLoadCallback callback) {
        String userHomeDirectory = System.getProperty("user.home");
        String filePath = userHomeDirectory + File.separator + "passwords.ser";

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Object object = inputStream.readObject();
            if (object instanceof List) {
                passwordEntries = (List<PasswordEntry>) object;
                callback.onPasswordLoadSuccess(passwordEntries);
            } else {
                callback.onPasswordLoadError("Invalid data format in the password file.");
            }
        } catch (FileNotFoundException e) {
            callback.onPasswordLoadError("Password file not found.");
        } catch (IOException | ClassNotFoundException e) {
            callback.onPasswordLoadError("Error loading password file: " + e.getMessage());
        }
    }
}
