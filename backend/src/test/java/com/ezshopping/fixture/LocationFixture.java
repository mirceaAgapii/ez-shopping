package com.ezshopping.fixture;

import com.ezshopping.location.model.entity.Location;

public class LocationFixture {

    private static final Location locationStore;
    private static final Location locationCart;

    static {
        locationStore = new Location();
        locationStore.setId("testId");
        locationStore.setLocationType("STORE");

        locationCart = new Location();
        locationCart.setId("testId");
        locationCart.setLocationType("CART");
    }

    public static Location locationStore() {
        return locationStore;
    }

    public static Location locationCart() {
        return locationCart;
    }
}
