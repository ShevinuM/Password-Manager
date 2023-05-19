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

    public static void addFolder(String folderName) {
        Folder newFolder = new Folder(folderName);
        folderMap.put(folderName, newFolder);
    }

    public static Folder getFolderByName(String folderName) {
        return folderMap.get(folderName);
    }

    public static class Folder {
        private final String folderName;

        public Folder(String folderName) {
            this.folderName = folderName;
        }

        public String getFolderName() {
            return folderName;
        }
    }

}
