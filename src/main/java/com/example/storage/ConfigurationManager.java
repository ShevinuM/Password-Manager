package com.example.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.module.Configuration;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Handles the management of the configuration file for the application.
 */
public class ConfigurationManager {

    private static final String CONFIG_FILE_NAME = "config.json";
    private static final String CONFIG_FOLDER_NAME = "config";
    private static final String CONFIG_FOLDER_PATH = System.getProperty("user.home") + "/" + CONFIG_FOLDER_NAME;
    private static final String CONFIG_FILE_PATH = CONFIG_FOLDER_PATH + "/" + CONFIG_FILE_NAME;

    /**
     * Creates the configuration file if it does not exist.
     */
    public static void createConfigFile() {
        if (!isConfigFileExists()) {
            try {
                Files.createDirectories(Paths.get(CONFIG_FOLDER_PATH));
                Files.createFile(Paths.get(CONFIG_FILE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the path to the configuration file.
     * @return The path to the configuration file.
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
     * @return The configuration file.
     */
    public static Configuration loadConfigFile() {
        Configuration configuration = null;
        if (isConfigFileExists()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                configuration = objectMapper.readValue(new File(CONFIG_FILE_PATH), Configuration.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }



}
