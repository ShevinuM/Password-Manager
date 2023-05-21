package com.example.config;

public class AppConfiguration {

    private final String fileLocation;

    public AppConfiguration(ConfigurationFile config) {
        this.fileLocation = config.fileLocation();
    }

    public String getFileLocation() {
        return fileLocation;
    }
}
