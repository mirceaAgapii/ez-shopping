package com.ezshopping.user.exceptions;

public class UserCantBeDeletedException extends RuntimeException{

    public UserCantBeDeletedException(String message) {
        super(message);
    }
}
