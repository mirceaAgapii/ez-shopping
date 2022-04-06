package com.ezshopping.arduino.model.type;

import lombok.Getter;

public enum ArduinoReadType {
    RF("RF"),
    QR("QR");
    private String value;

    ArduinoReadType(String value) {
        this.value = value;
    }
}
