package com.ezshopping.product.exceptions;

public class ProductAlreadyInDatabaseException extends RuntimeException {
    public ProductAlreadyInDatabaseException(String message) {
        super(message);
    }
}
