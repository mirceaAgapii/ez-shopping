package com.ezshopping.order.controller;

import com.ezshopping.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ezshopping.api.EndpointsAPI.API;
import static com.ezshopping.api.EndpointsAPI.ORDERS;

@RestController
@RequestMapping(API + ORDERS)
@Slf4j
public class OrderControllerREST {

    private final OrderService orderService;

    public OrderControllerREST(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/finish/{orderId}")
    public ResponseEntity<Void> finishOrder(@PathVariable String orderId) {
        log.info("Received POST request for finishing order [{}]", orderId);
        orderService.finishOrderById(orderId);
        return ResponseEntity.ok().build();
    }

}
