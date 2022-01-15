package com.ezshopping.config.http.filter;

import org.springframework.http.HttpStatus;

public enum HttpStatusEZ {

    USER_NOT_FOUND(461, HttpStatus.Series.CLIENT_ERROR, "User not found"),
    USER_EXISTS(462, HttpStatus.Series.CLIENT_ERROR, "User already in database");

    private final int value;
    private final HttpStatus.Series series;
    private final String reasonPhrase;

    HttpStatusEZ(int value, HttpStatus.Series series, String reasonPhrase) {
        this.value = value;
        this.series = series;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

}
