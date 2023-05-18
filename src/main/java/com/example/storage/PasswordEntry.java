package com.example.storage;

import java.io.Serializable;
import java.util.Objects;

// Represents a single password entry with attributes like username, password, and associated website or application.
public class PasswordEntry implements Serializable {


    private static final long serialVersionUID = 1L;
    private final String username;
    private final String password;
    private final String website;

    public PasswordEntry(String username, String password, String website) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (website == null || website.isEmpty()) {
            throw new IllegalArgumentException("Website cannot be null or empty");
        }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        PasswordEntry other = (PasswordEntry) obj;
        return Objects.equals(username, other.username)
                && Objects.equals(password, other.password)
                && Objects.equals(website, other.website);
    }

}
