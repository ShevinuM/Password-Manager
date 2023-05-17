package com.example.storage;

import java.io.Serializable;

// Represents a single password entry with attributes like username, password, and associated website or application.
public class PasswordEntry implements Serializable {


    private static final long serialVersionUID = 1L;
    private final String username;
    private final String password;
    private final String website;

    public PasswordEntry(String username, String password, String website) {
        this.username = username;
        this.password = password;
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getWebsite() {
        return website;
    }

}
