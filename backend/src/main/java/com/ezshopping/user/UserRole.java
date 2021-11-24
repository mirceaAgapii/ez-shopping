package com.ezshopping.user;

import lombok.Getter;

@Getter
public enum UserRole {

    CLIENT("CLIENT"),
    ADMINISTRATOR("ADMINISTRATOR");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
