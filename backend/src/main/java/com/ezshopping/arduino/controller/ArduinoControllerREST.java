package com.ezshopping.arduino.controller;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.arduino.service.ArduinoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointsAPI.API + "/arduino")
@Slf4j
public class ArduinoControllerREST {

    private final ArduinoService arduinoService;

    @Autowired
    public ArduinoControllerREST(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    @PostMapping("/rf")
    public void onRFRead(@RequestBody String payload) {
        arduinoService.handleRFRead(payload);
    }

    @PostMapping("/qr")
    public void onQRCodeScan(@RequestBody String payload) {
        arduinoService.handleQRScan(payload);
    }
}
