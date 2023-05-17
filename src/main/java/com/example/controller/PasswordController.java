package com.example.controller;

import com.example.storage.PasswordEntry;

public class PasswordController {

    public void createAndStorePassword(String username, String password, String website) {
        PasswordEntry passwordObj = new PasswordEntry(username, password, website);
        passwordObj.storePassword(passwordObj);
    }


}
