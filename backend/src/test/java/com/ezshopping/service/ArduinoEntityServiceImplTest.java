package com.ezshopping.service;

import com.ezshopping.arduino.model.entity.PayloadData;
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
class ArduinoEntityServiceImplTest {

    private static final String WS_TOKEN = "WS_TOKEN";
    private static final String WS_NAME = "WS_NAME";
    private static final String READ_TYPE = "READ_TYPE";
    private static final String DATA = "DATA";

    @Mock
    private ArduinoRepository arduinoRepository;
    @Mock
    private TextWebSocketHandlerEZ textWebSocketHandler;
    @InjectMocks
    private ArduinoServiceImpl arduinoService;

    private PayloadData payloadData;

    @BeforeEach
    void setup() {
        when(arduinoRepository.existsByIdAndWorkstationName("testToken", "testName")).thenReturn(true);
        payloadData = new PayloadData();
        payloadData.setWorkstationId("testToken");
        payloadData.setWorkstationName("testName");
        payloadData.setReadType("testType");
        payloadData.setData("testData");
    }

    @Test
    void isControllerKnown_whenInvokedWithValidPayload_returnsTrue() {
        assertThat(arduinoService.isControllerKnown(payloadData)).isTrue();
    }


}