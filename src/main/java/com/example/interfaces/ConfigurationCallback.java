package com.example.interfaces;

import com.example.storage.Configuration;

public interface ConfigurationCallback {

    void onConfigFileCreateSuccess();

    void onConfigFileCreateError(String errorMessage);

    void onConfigFileLoadSuccess(Configuration configuration);

    void onConfigFileLoadError(String errorMessage);

    void onConfigFileSaveSuccess();

    void onConfigFileSaveError(String errorMessage);

    void onConfigFileDeleteSuccess();

    void onConfigFileDeleteError(String errorMessage);

    void onUpdateConfigSuccess();

    void onUpdateConfigError(String errorMessage);
}
