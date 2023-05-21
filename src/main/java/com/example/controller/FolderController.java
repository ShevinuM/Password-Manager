package com.example.controller;

import com.example.storage.SerializationFolderStorage;

public class FolderController {

    public void addFolder() {
        // TODO: Implement the UI for the folder
        // TODO: Get the folder name from the UI
        String folderName = "folderName"; // Temporary value to get rid of error
        while (true) {
            if (SerializationFolderStorage.folderExists(folderName)) {
                // TODO: Display to the user that the folder already exists and let the user try again.
            } else {
                SerializationFolderStorage.addFolder(folderName);
                break;
            }

            // TODO: Implement the scenario where the user cancels the operation
        }
    }

}
