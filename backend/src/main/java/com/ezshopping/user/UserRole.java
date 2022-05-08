package com.ezshopping.user;

import lombok.Getter;

@Getter
public enum UserRole {

    CLIENT("CLIENT"),
    ADMINISTRATOR("ADMINISTRATOR"),
    CHECKOUT("CHECKOUT"),
    RECEIVING("RECEIVING");


    private final String value;

    UserRole(String value) {
        this.value = value;
    }
}
