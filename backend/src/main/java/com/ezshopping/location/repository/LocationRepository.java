package com.ezshopping.location.repository;

import com.ezshopping.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findById(String id);
}
