package com.ezshopping.arduino.model;

import com.ezshopping.common.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ez_iot_controller")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
public class ArduinoController extends AbstractEntity {

    @Column(name = "workstation_name")
    private String workstationName;
}
