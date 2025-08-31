package com.ila.preferences;

public class User {
    private String username;
    private String email;
    private String password;
    private String userType;
    private String gradeLevel;

    public User(String username, String email, String password, String userType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.gradeLevel = "1st Grade"; // Default grade level
    }

    public User(String username, String email, String password, String userType, String gradeLevel) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.gradeLevel = gradeLevel;
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getUserType() { return userType; }
    public String getGradeLevel() { return gradeLevel; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setUserType(String userType) { this.userType = userType; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }
}
