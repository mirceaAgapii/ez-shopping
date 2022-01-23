package com.ezshopping.fixture;

import com.ezshopping.location.model.dto.LocationDTO;

public class LocationDTOFixture {

    private static final LocationDTO locationDTO;

    static {
        locationDTO = LocationDTO.builder()
                .locationType("STORE")
                .id("testId")
                .build();
    }

    public static LocationDTO locationDTO() {
        return locationDTO;
    }
}
