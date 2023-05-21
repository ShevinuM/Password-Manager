package com.example.interfaces;

import com.example.config.ConfigurationFile;

public interface ConfigurationFileCallback {

    void onConfigFileCreateSuccess();

    void onConfigFileCreateError(String errorMessage);

    void onConfigFileLoadSuccess(ConfigurationFile configuration);

    void onConfigFileLoadError(String errorMessage);

    void onConfigFileSaveSuccess();

    void onConfigFileSaveError(String errorMessage);

    void onConfigFileDeleteSuccess();

    void onConfigFileDeleteError(String errorMessage);

    void onUpdateConfigSuccess();

    void onUpdateConfigError(String errorMessage);
}
