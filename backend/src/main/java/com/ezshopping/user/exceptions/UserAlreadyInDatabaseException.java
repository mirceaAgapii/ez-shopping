package com.ezshopping.user.exceptions;

public class UserAlreadyInDatabaseException extends RuntimeException{

    public UserAlreadyInDatabaseException(String message){
        super(message);
    }
}
