package com.ezshopping.location.mapper;

import com.ezshopping.common.Mapper;
import com.ezshopping.location.model.dto.LocationDTO;
import com.ezshopping.location.model.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationDTOMapper implements Mapper<Location, LocationDTO> {
    @Override
    public LocationDTO map(Location entity) {
        return LocationDTO.builder()
                .id(entity.getId())
                .locationType(entity.getLocationType())
                .build();
    }
}
