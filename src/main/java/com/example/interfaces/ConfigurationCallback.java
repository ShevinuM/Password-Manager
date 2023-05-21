package com.example.interfaces;

public interface ConfigurationCallback {

    void onConfigFileCreateSuccess();

    void onConfigFileCreateError(String errorMessage);

    void onConfigFileLoadSuccess();

    void onConfigFileLoadError(String errorMessage);

    void onConfigFileSaveSuccess();

    void onConfigFileSaveError(String errorMessage);

    void onConfigFileDeleteSuccess();

    void onConfigFileDeleteError(String errorMessage);

    void onUpdateConfigSuccess();

    void onUpdateConfigError(String errorMessage);
}
