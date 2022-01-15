package com.ezshopping.arduino.repository;

import com.ezshopping.arduino.model.ArduinoController;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArduinoRepository extends JpaRepository<ArduinoController,String> {

    Optional<ArduinoController> getByIdAndWorkstationName(String id, String workstationName);
    boolean existsByIdAndWorkstationName(String id, String workstationName);
}
