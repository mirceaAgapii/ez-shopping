package com.ezshopping.arduino.repository;

import com.ezshopping.arduino.model.entity.Arduino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArduinoRepository extends JpaRepository<Arduino,String> {

    Optional<Arduino> getByIdAndWorkstationName(String id, String workstationName);
    boolean existsByIdAndWorkstationName(String id, String workstationName);
}
