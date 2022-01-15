package com.ezshopping.arduino.service;

public interface ArduinoService {

    boolean isControllerKnown(String payload);

    void handleRFRead(String payload);
}
