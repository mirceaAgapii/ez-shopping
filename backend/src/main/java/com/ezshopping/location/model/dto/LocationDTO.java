package com.ezshopping.location.model.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
public class LocationDTO {
    private String id;

    private String locationType;
}
