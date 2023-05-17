package com.example.storage;

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

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(passwordEntries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the password entries from a file in the user's home directory using object deserialization.
     * The file should be saved with the predetermined name.
     */
    public void loadPasswords() {
        String userHomeDirectory = System.getProperty("user.home");
        String filePath = userHomeDirectory + File.separator + "passwords.ser";

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            passwordEntries = (List<PasswordEntry>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
