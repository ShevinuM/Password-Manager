package com.example.controller;

import com.example.interfaces.ConfigurationCallback;
import com.example.storage.ConfigurationManager;

public class ConfigurationController implements ConfigurationCallback {

    public void start() {
        if (ConfigurationManager.isConfigFileExists()) {
            ConfigurationManager.loadConfigFile();
        } else {
            ConfigurationManager.createConfigFile();
        }
    }

    @Override
    public void onConfigFileCreateSuccess() {
        // TODO: Implement what to do when the config file is created successfully.
    }

    @Override
    public void onConfigFileCreateError(String errorMessage) {
        // TODO: Implement what to do when an error occurs during config file creation.
    }

    @Override
    public void onConfigFileLoadSuccess() {
        // TODO: Implement what to do when the config file is loaded successfully.
    }

    @Override
    public void onConfigFileLoadError(String errorMessage) {
        // TODO: Implement what to do when an error occurs during config file loading.
    }

    @Override
    public void onConfigFileSaveSuccess() {
        // TODO: Implement what to do when the config file is saved successfully.
    }

    @Override
    public void onConfigFileSaveError(String errorMessage) {
        // TODO: Implement what to do when an error occurs during config file saving.
    }

    @Override
    public void onConfigFileDeleteSuccess() {
        // TODO: Implement what to do when the config file is deleted successfully.
    }

    @Override
    public void onConfigFileDeleteError(String errorMessage) {
        // TODO: Implement what to do when an error occurs during config file deletion.
    }

    @Override
    public void onUpdateConfigSuccess() {
        // TODO: Implement what to do when the config file is updated successfully.
    }

    @Override
    public void onUpdateConfigError(String errorMessage) {
        // TODO: Implement what to do when an error occurs during config file update.
    }

}
