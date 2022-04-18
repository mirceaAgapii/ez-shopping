package com.ezshopping.websocket.service;

import com.ezshopping.arduino.model.dto.RfScanDTO;
import com.ezshopping.order.model.entity.Order;
import com.ezshopping.order.orderline.mapper.OrderLineDTOMapper;
import com.ezshopping.order.orderline.model.entity.OrderLine;
import com.ezshopping.order.orderline.service.OrderLineService;
import com.ezshopping.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebSocketService {

    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final OrderLineDTOMapper orderLineDTOMapper;
    private final ObjectMapper objectMapper;

    public WebSocketService(OrderService orderService,
                            OrderLineService orderLineService,
                            OrderLineDTOMapper orderLineDTOMapper,
                            ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.orderLineService = orderLineService;
        this.orderLineDTOMapper = orderLineDTOMapper;
        this.objectMapper = objectMapper;
    }

    /**
     * Message through websocket will be sent to client with active order
     *
     * @param userId        user to whom the dto will be sent
     * @param session       started by the user
     * @throws IOException  will be thrown in case of  an exception
     */
    public void sendActiveOrderToClient(String userId, WebSocketSession session) throws IOException {
        Order order = orderService.createIfNotExistsForUser(userId);
        List<OrderLine> orderLines = orderLineService.getAllByParent(order);
        RfScanDTO dto = RfScanDTO.builder()
                .userId(userId)
                .orderId(order.getId())
                .orderLines(orderLines.stream().map(orderLineDTOMapper::map).collect(Collectors.toList()))
                .finished(false)
                .build();
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(dto)));
    }
}
