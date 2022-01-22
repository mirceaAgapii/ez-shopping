package com.ezshopping.arduino.repository;

import com.ezshopping.arduino.model.Arduino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArduinoRepository extends JpaRepository<Arduino,String> {

    Optional<Arduino> getByIdAndWorkstationName(String id, String workstationName);
    boolean existsByIdAndWorkstationName(String id, String workstationName);
}
