package com.ezshopping.arduino.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayloadData {
    private String workstationId;
    private String workstationName;
    private String readType;
    private String userId;
    private String data;
}
