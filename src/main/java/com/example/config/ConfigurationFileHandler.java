package com.example.config;

import com.example.interfaces.ConfigurationFileCallback;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Handles the management of the configuration file for the application.
 */
public class ConfigurationFileHandler {

    private static final String CONFIG_FILE_NAME = "config.json";
    private static final String CONFIG_FOLDER_NAME = "SafePassConfig";
    private static final String CONFIG_FOLDER_PATH = System.getProperty("user.home") + "/" + CONFIG_FOLDER_NAME;
    private static final String CONFIG_FILE_PATH = CONFIG_FOLDER_PATH + "/" + CONFIG_FILE_NAME;

    /**
     * Creates the configuration file.
     * @param filepath The file path to be stored in the configuration file.
     * @param callback The callback to be called when the configuration file is created / not created.
     */
    public static void createConfigFile(String filepath, ConfigurationFileCallback callback) {
        if (!isConfigFileExists()) {
            try {
                Files.createDirectories(Paths.get(CONFIG_FOLDER_PATH));
                Files.createFile(Paths.get(CONFIG_FILE_PATH));
                updateConfig(filepath, callback); // Save the initial configuration to the file
                callback.onConfigFileCreateSuccess();
            } catch (IOException e) {
                callback.onConfigFileCreateError("IOException");
            } catch (SecurityException e) {
                e.printStackTrace();
                callback.onConfigFileCreateError("SecurityException");
            }
        }
    }

    /**
     * Updates the configuration in the configuration file.
     * @param filePath The file path to be stored in the configuration file.
     * @param callback The callback to be called when the configuration file is updated / not updated.
     */
    public static void updateConfig(String filePath, ConfigurationFileCallback callback) {
        ConfigurationFile configuration = new ConfigurationFile(filePath);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(CONFIG_FILE_PATH), configuration);
            callback.onUpdateConfigSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            callback.onUpdateConfigError("IOException");
        }
    }

    /**
     * Returns the path the configuration file gets stored.
     * @return The path the configuration file gets stored.
     */
    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

    /**
     * Checks if the configuration file exists.
     * @return true if the configuration file exists, false otherwise.
     */
    public static boolean isConfigFileExists() {
        return Files.exists(Paths.get(CONFIG_FILE_PATH));
    }

    /**
     * Loads the configuration file.
     * @param callback The callback to be called when the configuration file is loaded / not loaded.
     */
    public static void loadConfigFile(ConfigurationFileCallback callback) {
        ConfigurationFile configuration;
        if (isConfigFileExists()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                configuration = objectMapper.readValue(new File(CONFIG_FILE_PATH), ConfigurationFile.class);
                callback.onConfigFileLoadSuccess(configuration);
            } catch (IOException e) {
                e.printStackTrace();
                callback.onConfigFileLoadError("IOException");
            }
        } else {
            callback.onConfigFileLoadError("file_does_not_exist");
        }

    }

    /**
     * Deletes the configuration file.
     */
    public static void deleteConfigFile(ConfigurationFileCallback callback) {
        if (isConfigFileExists()) {
            try {
                Files.delete(Paths.get(CONFIG_FILE_PATH));
                callback.onConfigFileDeleteSuccess();
            } catch (IOException e) {
                e.printStackTrace();
                callback.onConfigFileDeleteError("IOException");
            }
        }
    }

}
