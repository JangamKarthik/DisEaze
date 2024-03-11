package com.example.diseaze;

public class User {
    private String userId;
    private String name;
    private String number;
    private String email;
    private String gender;
    private String password;

    // Required empty constructor for Firebase
    public User() {
    }

    public User(String userId, String name, String number, String email, String gender, String password) {
        this.userId = userId;
        this.name = name;
        this.number = number;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){ return password; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
