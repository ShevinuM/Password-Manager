package com.example.storage;

import java.io.Serializable;

// Represents a single password entry with attributes like username, password, and associated website or application.
public record PasswordEntry(String username, String password, String website) implements Serializable {}
