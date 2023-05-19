package com.example.storage;

import com.example.interfaces.PasswordLoadCallback;
import com.example.interfaces.PasswordSaveCallback;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SerializationPasswordStorage {

    private List<PasswordEntry> passwordEntries;

    public SerializationPasswordStorage() {
        passwordEntries = new ArrayList<>();
    }

    /**
     * Adds a PasswordEntry to the password storage.
     * @param passwordEntry The PasswordEntry to be added.
     */
    public synchronized void addPasswordEntry(PasswordEntry passwordEntry) {
        if (passwordEntry != null) {
            if (passwordEntries.contains(passwordEntry)) {
                throw new IllegalArgumentException("PasswordEntry already exists in the storage");
            } else {
                passwordEntries.add(passwordEntry);
            }
        } else {
            throw new NullPointerException("PasswordEntry cannot be null");
        }
    }

    /**
     * Removes a PasswordEntry from the password storage.
     * @param passwordEntry The PasswordEntry to be removed.
     */
    public synchronized void removePasswordEntry(PasswordEntry passwordEntry) {
        if (passwordEntry != null) {
            if (passwordEntries.contains(passwordEntry)) {
                passwordEntries.remove(passwordEntry);
            } else {
                throw new IllegalArgumentException("PasswordEntry does not exist in the storage");
            }
        } else {
            throw new NullPointerException("PasswordEntry cannot be null");
        }
    }

    /**
     * Retrieves the list of password entries.
     * @return The list of password entries.
     */
    public List<PasswordEntry> getPasswordEntries() {
        return passwordEntries;
    }

    /**
     * Clears the password entries from the password storage.
     */
    public void clear() {
        passwordEntries.clear();
    }

    /**
     * Saves the password entries to a file in the user's home directory using object serialization.
     * The file will be saved with a predetermined name 'passwords.ser'
     * @param callback The callback interface to handle the password save result.  The callback methods will be invoked
     *                 to notify the caller about the success or failure of the operation.
     */
    public synchronized void savePasswords(PasswordSaveCallback callback) {
        String userHomeDirectory = System.getProperty("user.home");
        String filePath = userHomeDirectory + File.separator + "passwords.ser";

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            if (Files.exists(Paths.get(filePath))) outputStream.reset(); // File already exists, overwrite its contents
            outputStream.writeObject(passwordEntries);
            callback.onPasswordSaveSuccess();
        } catch (IOException e) {
            callback.onPasswordSaveError("Failed to save passwords.");
            e.printStackTrace();
        } catch (SecurityException e) {
            callback.onPasswordSaveError("Insufficient permissions to write to the file");
            e.printStackTrace();
        } catch (Exception e) {
            callback.onPasswordSaveError("An unexpected error occured while saving passwords.");
            e.printStackTrace();
        }
    }

    /**
     * Loads the password entries from a file in the user's home directory using object deserialization.
     * The file should be saved with the predetermined name.
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
        } catch (SecurityException e) {
            callback.onPasswordLoadError("Insufficient permissions to read the password file.");
        } catch (ClassCastException e) {
            callback.onPasswordLoadError("Invalid data format in the password file. Unable to cast to List<PasswordEntry>.");
        }
    }
}
