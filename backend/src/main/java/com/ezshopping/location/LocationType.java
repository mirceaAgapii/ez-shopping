package com.ezshopping.location;

import lombok.Getter;

@Getter
public enum LocationType {
    STORE("STORE"),
    CART("CART");

    private final String value;

    LocationType(String value) {
       this.value = value;
    }
}
