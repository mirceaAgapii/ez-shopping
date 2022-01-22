package com.ezshopping.location.service;

import com.ezshopping.location.exceptions.LocationNotFoundException;
import com.ezshopping.location.model.entity.Location;
import com.ezshopping.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location getById(String id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location with id [" + id + "] not found"));
    }
}
