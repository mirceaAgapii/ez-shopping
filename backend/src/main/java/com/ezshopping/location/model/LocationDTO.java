package com.ezshopping.location.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
public class LocationDTO {
    private String id;
    @NotNull
    private String locationType;
}
