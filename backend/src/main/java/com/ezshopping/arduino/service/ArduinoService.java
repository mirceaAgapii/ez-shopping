package com.ezshopping.arduino.service;

import com.ezshopping.arduino.model.entity.PayloadData;

public interface ArduinoService {

    boolean isControllerKnown(PayloadData payloadData);

    void handleRFRead(String payload);

    void handleQRScan(String payload);
}
