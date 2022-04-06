package com.ezshopping.arduino.repository;

import com.ezshopping.arduino.model.entity.ArduinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArduinoRepository extends JpaRepository<ArduinoEntity,String> {

    Optional<ArduinoEntity> getByIdAndWorkstationName(String id, String workstationName);
    boolean existsByIdAndWorkstationName(String id, String workstationName);
}
