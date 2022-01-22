package com.ezshopping.service;

import com.ezshopping.arduino.repository.ArduinoRepository;
import com.ezshopping.arduino.service.ArduinoServiceImpl;
import com.ezshopping.websocket.handler.TextWebSocketHandlerEZ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArduinoServiceImplTest {

    @Mock
    private ArduinoRepository arduinoRepository;
    @Mock
    private TextWebSocketHandlerEZ textWebSocketHandler;
    @InjectMocks
    private ArduinoServiceImpl arduinoService;

    private String testPayload = "uuid;WS1;10 20 30 40;";

    @BeforeEach
    void setup() {
        when(arduinoRepository.existsByIdAndWorkstationName("uuid", "WS1")).thenReturn(true);
    }

    @Test
    void isControllerKnown_whenInvokedWithValidPayload_returnsTrue() {
        assertThat(arduinoService.isControllerKnown(testPayload)).isTrue();
    }


}