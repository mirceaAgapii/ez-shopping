package com.example.ezshoppingapp.model;

import com.google.gson.annotations.SerializedName;

public class UserRegistrationDTO {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("email")
    String email;

    public UserRegistrationDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
