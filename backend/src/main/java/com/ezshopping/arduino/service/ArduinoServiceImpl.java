package com.ezshopping.arduino.service;

import com.ezshopping.arduino.exception.ArduinoControllerNotFoundException;
import com.ezshopping.arduino.model.dto.RfScanDTO;
import com.ezshopping.arduino.model.entity.ArduinoEntity;
import com.ezshopping.arduino.model.entity.PayloadData;
import com.ezshopping.arduino.model.type.ArduinoReadType;
import com.ezshopping.arduino.repository.ArduinoRepository;
import com.ezshopping.order.model.entity.Order;
import com.ezshopping.order.orderline.mapper.OrderLineDTOMapper;
import com.ezshopping.order.orderline.model.dto.OrderLineDTO;
import com.ezshopping.order.orderline.model.entity.OrderLine;
import com.ezshopping.order.orderline.service.OrderLineService;
import com.ezshopping.order.service.OrderService;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.websocket.handler.TextWebSocketHandlerEZ;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArduinoServiceImpl implements ArduinoService {

    private static final String WORKSTATION_TYPE_RECEIVING = "RECEIVING";
    private static final String WORKSTATION_TYPE_CHECKOUT = "CHECKOUT";
    private static final String USER_ID_ATTRIBUTE = "USER_ID";

    private final ArduinoRepository arduinoRepository;
    private final TextWebSocketHandlerEZ textWebSocketHandler;
    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final OrderLineDTOMapper orderLineDTOMapper;

    private static final String STATION_ATTRIBUTE = "station";

    @Autowired
    public ArduinoServiceImpl(TextWebSocketHandlerEZ textWebSocketHandler,
                              ArduinoRepository arduinoRepository,
                              OrderService orderService,
                              OrderLineService orderLineService,
                              ProductService productService, ObjectMapper objectMapper, OrderLineDTOMapper orderLineDTOMapper) {
        this.textWebSocketHandler = textWebSocketHandler;
        this.arduinoRepository = arduinoRepository;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
        this.productService = productService;
        this.objectMapper = objectMapper;
        this.orderLineDTOMapper = orderLineDTOMapper;
    }

    @Override
    public void handleRFRead(String payload) {
        PayloadData payloadData = decodePayload(payload);

        if(isControllerKnown(payloadData)) {
            ArduinoEntity arduinoEntity = arduinoRepository.getByIdAndWorkstationName(payloadData.getWorkstationId(), payloadData.getWorkstationName())
                    .orElseThrow(() -> new ArduinoControllerNotFoundException(" not found"));
            switch (arduinoEntity.getWorkstationType()) {
                case WORKSTATION_TYPE_RECEIVING:
                    handleReceivingRead(payloadData);
                    break;
                case WORKSTATION_TYPE_CHECKOUT:
                    handleCheckoutRead(payloadData);
                    break;
            }
        }
    }

    @Override
    public void handleQRScan(String payload) {
        PayloadData payloadData = decodePayload(payload);

        if(isControllerKnown(payloadData)) {
            try {
            WebSocketSession session = textWebSocketHandler
                    .getSessionByAttributeValue(STATION_ATTRIBUTE, payloadData.getWorkstationName());
            session.getAttributes().put(USER_ID_ATTRIBUTE, payloadData.getUserId());

            Order order = orderService.checkActiveOrderForUser(payloadData.getUserId());
            List<OrderLine> orderLines = orderLineService.getAllByParent(order);
            RfScanDTO dto = RfScanDTO.builder()
                .orderId(order.getId())
                .orderLines(orderLines.stream().map(orderLineDTOMapper::map).collect(Collectors.toList()))
                .finished(false)
                .build();
             session.sendMessage(new TextMessage(objectMapper.writeValueAsString(dto)));
            } catch (IOException e) {
                log.error("Error during sending message via WebSocket to workstation [{}]", payloadData.getWorkstationName());
            } catch (RuntimeException e) {
                log.error("No session found for station [{}]. Ignore payload", payloadData.getWorkstationName());
            }
        }
    }

    @Override
    public boolean isControllerKnown(PayloadData payloadData) {
        return arduinoRepository.existsByIdAndWorkstationName(payloadData.getWorkstationId(), payloadData.getWorkstationName());
    }

    private PayloadData decodePayload(String payload) {
        PayloadData payloadData = new PayloadData();

        payloadData.setWorkstationId(getFirstElementFromPayload(payload));
        payload = removeFirstElementFromPayload(payload);

        payloadData.setWorkstationName(getFirstElementFromPayload(payload));
        payload = removeFirstElementFromPayload(payload);

        payloadData.setReadType(getFirstElementFromPayload(payload));
        payload = removeFirstElementFromPayload(payload);

        if(payloadData.getReadType().equals(ArduinoReadType.QR.toString())) {
            payloadData.setUserId(getFirstElementFromPayload(payload));
        } else {
            payloadData.setUserId(getFirstElementFromPayload(payload));
            payload = removeFirstElementFromPayload(payload);

            payloadData.setData(getFirstElementFromPayload(payload));
        }
        return payloadData;
    }

    private void handleReceivingRead(PayloadData payloadData) {
        try {
            textWebSocketHandler
                    .getSessionByAttributeValue(STATION_ATTRIBUTE, payloadData.getWorkstationName())
                    .sendMessage(new TextMessage("{\"payload\":\"" + payloadData.getData() + "\"}"));
        } catch (IOException e) {
            log.error("Error during sending message via WebSocket to workstation [{}]", payloadData.getWorkstationName());
        } catch (RuntimeException ex) {
            log.error("No session found for station [{}]. Ignore payload", payloadData.getWorkstationName());
        }
    }

    private void handleCheckoutRead(PayloadData payloadData) {
        try {
            WebSocketSession session = textWebSocketHandler.getSessionByAttributeValue(STATION_ATTRIBUTE, payloadData.getWorkstationName());

            Order order = orderService.findByUserId(payloadData.getUserId());
            OrderLine orderLine = orderLineService.createUpdateOrderLineForOrder(order, productService.getProductByRfId(payloadData.getData()));

            OrderLineDTO orderLineDTO = OrderLineDTO.builder()
                    .orderLineId(orderLine.getId())
                    .productId(orderLine.getProduct().getRfId())
                    .productName(orderLine.getProduct().getName())
                    .olQty(orderLine.getQuantity())
                    .build();

            RfScanDTO dto = RfScanDTO.builder()
                            .orderId(order.getId())
                            .orderLineDTO(orderLineDTO)
                            .totalQty(order.getTotal())
                            .finished(false)
                            .build();

            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(dto)));

        } catch (RuntimeException ex) {
            log.error("No session found for station [{}]. Ignore payload", payloadData.getWorkstationName());
        } catch (IOException e) {
            log.error("Error during sending message via WebSocket to workstation [{}]", payloadData.getWorkstationName());
        }
    }

    private String getFirstElementFromPayload(String payload) {

        return payload.length() < 1 ? "" : payload.substring(0, payload.indexOf(";"));
    }

    private String removeFirstElementFromPayload(String payload) {
        return payload.length() < 1 ? "" : payload.substring(payload.indexOf(";") + 1);
    }
}
