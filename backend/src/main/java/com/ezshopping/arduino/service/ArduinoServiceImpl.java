package com.ezshopping.arduino.service;

import com.ezshopping.arduino.repository.ArduinoRepository;
import com.ezshopping.websocket.handler.TextWebSocketHandlerEZ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import java.io.IOException;

@Service
@Slf4j
public class ArduinoServiceImpl implements ArduinoService {

    private final ArduinoRepository arduinoRepository;
    private final TextWebSocketHandlerEZ textWebSocketHandler;

    @Autowired
    public ArduinoServiceImpl(TextWebSocketHandlerEZ textWebSocketHandler,
                              ArduinoRepository arduinoRepository) {
        this.textWebSocketHandler = textWebSocketHandler;
        this.arduinoRepository = arduinoRepository;
    }

    @Override
    public boolean isControllerKnown(String payload) {
        String id = payload.substring(0, payload.indexOf(";"));
        payload = removeFirstElementFromPayload(payload);
        String workstation = payload.substring(0, payload.indexOf(";"));

        return arduinoRepository.existsByIdAndWorkstationName(id, workstation);
    }

    @Override
    public void handleRFRead(String payload) {
        if(isControllerKnown(payload)) {
            payload = removeFirstElementFromPayload(payload);
            String workstation = payload.substring(0, payload.indexOf(";"));
            payload = removeFirstElementFromPayload(payload);
            String rfId = payload.substring(0, payload.indexOf(";"));
            try {
                textWebSocketHandler
                        .getSessionByAttributeAndValue("station", workstation)
                        .sendMessage(new TextMessage("{\"payload\":\"" + rfId + "\"}"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException ex) {
                log.error("No session found for station [{}]. Ignore payload", workstation);
            }
        }

    }

    private String removeFirstElementFromPayload(String payload) {
        return payload.substring(payload.indexOf(";") + 1);
    }
}
