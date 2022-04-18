package com.ezshopping.arduino.service;

import com.ezshopping.arduino.exception.ArduinoControllerNotFoundException;
import com.ezshopping.arduino.model.entity.ArduinoEntity;
import com.ezshopping.arduino.model.entity.PayloadData;
import com.ezshopping.arduino.model.type.ArduinoReadType;
import com.ezshopping.arduino.repository.ArduinoRepository;
import com.ezshopping.order.model.entity.Order;
import com.ezshopping.order.orderline.model.entity.OrderLine;
import com.ezshopping.order.orderline.service.OrderLineService;
import com.ezshopping.order.service.OrderService;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.websocket.handler.TextWebSocketHandlerEZ;
import com.ezshopping.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static com.ezshopping.common.GeneralConstants.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArduinoServiceImpl implements ArduinoService {

    private final ArduinoRepository arduinoRepository;
    private final TextWebSocketHandlerEZ textWebSocketHandler;
    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final ProductService productService;
    private final WebSocketService webSocketService;

    @Autowired
    public ArduinoServiceImpl(TextWebSocketHandlerEZ textWebSocketHandler,
                              ArduinoRepository arduinoRepository,
                              OrderService orderService,
                              OrderLineService orderLineService,
                              ProductService productService,
                              WebSocketService webSocketService) {
        this.textWebSocketHandler = textWebSocketHandler;
        this.arduinoRepository = arduinoRepository;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
        this.productService = productService;
        this.webSocketService = webSocketService;
    }

    @Override
    public void handleRFRead(String payload) {
        PayloadData payloadData = decodePayload(payload);

        if(isControllerKnown(payloadData)) {
            ArduinoEntity arduinoEntity = arduinoRepository.getByIdAndWorkstationName(payloadData.getWorkstationId(), payloadData.getWorkstationName())
                    .orElseThrow(() -> new ArduinoControllerNotFoundException(" not found"));
            switch (arduinoEntity.getWorkstationType()) {
                case WORKSTATION_TYPE_CHECK_ARTICLE:
                case WORKSTATION_TYPE_RECEIVING:
                    handleArticleRead(payloadData);
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

            webSocketService.sendActiveOrderToClient(payloadData.getUserId(), session);

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

    @Override
    public ArduinoEntity getArduinoByName(String name) {
        return arduinoRepository.findByWorkstationName(name).orElseThrow(() -> new ArduinoControllerNotFoundException("Controller not found"));
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
            payloadData.setData(getFirstElementFromPayload(payload));
        }
        return payloadData;
    }

    private void handleArticleRead(PayloadData payloadData) {
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
            String userId = (String) session.getAttributes().get("userId");
            Order order = orderService.createIfNotExistsForUser(userId);
            List<OrderLine> orderLines = orderLineService.getAllByParent(order);
            Product product = productService.getProductByRfId(payloadData.getData());
            Optional<OrderLine> existingOrderLineOptional = orderLines.stream().filter(ol -> ol.getProduct().getId().equals(product.getId())).findAny();
            if (existingOrderLineOptional.isEmpty()) {
                orderLineService.createUpdateOrderLineForOrder(order, product);

                webSocketService.sendActiveOrderToClient(userId, session);
            } else {
                log.info("OrderLine with article [{}] already exists. Don't create new one", product.getId());
            }
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
