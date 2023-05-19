package com.example.storage;

import java.util.HashMap;
import java.util.Map;

public class FolderManager {

    private static final Map<String, Folder> folderMap = new HashMap<>();

    // Static block to initialize default folders
    static {
        addFolder("Websites");
        addFolder("Applications");
        addFolder("Email Accounts");
    }

    /**
     * Adds a folder to the folder map.
     * @param folderName The name of the folder to add.
     */
    public static void addFolder(String folderName) {
        Folder newFolder = new Folder(folderName);
        folderMap.put(folderName, newFolder);
    }

    /**
     * Returns the folder with the specified name.
     * @param folderName The name of the folder to retrieve.
     * @return The folder with the specified name.
     */
    public static Folder getFolderByName(String folderName) {
        return folderMap.get(folderName);
    }

    /**
     * Deletes the folder with the specified name.
     * @param folderName The name of the folder to delete.
     */
    public static void deleteFolder(String folderName) {
        folderMap.remove(folderName);
    }

    /**
     * Clears all folders except the default folders.
     */
    public static void clearFolders() {
        folderMap.entrySet().removeIf(entry -> !isDefaultFolder(entry.getKey()));
    }

    /**
     * Returns true if the folder with the specified name is a default folder.
     * @param folderName The name of the folder to check.
     * @return True if the folder with the specified name is a default folder.
     */
    private static boolean isDefaultFolder(String folderName) {
        return folderName.equals("Websites")
                || folderName.equals("Applications")
                || folderName.equals("Email Accounts");
    }

    public static class Folder {
        private final String folderName;

        public Folder(String folderName) {
            this.folderName = folderName;
        }

        /**
         * Returns the name of the folder.
         * @return The name of the folder.
         */
        public String getFolderName() {
            return folderName;
        }
    }
}
