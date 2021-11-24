package com.ezshopping.location;

import lombok.Getter;

@Getter
public enum LocationType {
    STORE("STORE"),
    CART("CART");

    private String Value;

    LocationType(String value) {
       this.Value = value;
    }
}
