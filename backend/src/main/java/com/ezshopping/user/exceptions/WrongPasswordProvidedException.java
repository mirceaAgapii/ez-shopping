package com.ezshopping.user.exceptions;

public class WrongPasswordProvidedException extends RuntimeException{
    public WrongPasswordProvidedException(String message) {
        super(message);
    }
}
