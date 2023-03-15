package com.example.nitcmag_e.Login_and_signup;

public class UserDetails {
    public String name,role,email;

    public UserDetails() {}

    public UserDetails(String name, String email) {
        this.name = name;
        this.email = email;
        this.role = "student";
    }
}
