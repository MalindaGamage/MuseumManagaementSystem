package model;

import java.util.UUID;

public class User {
    private UUID userId;
    private String username;
    private String password; // In a real-world application, store hashed passwords only
    private String role;
    private String email;
    private boolean isVisitor;

    // Constructor
    public User(UUID userId, String username, String password, String role, String email, boolean isVisitor) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.isVisitor = isVisitor;
    }

    // Getters
    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVisitor() {
        return isVisitor;
    }

    // Setters
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVisitor(boolean isVisitor) {
        this.isVisitor = isVisitor;
    }
}
