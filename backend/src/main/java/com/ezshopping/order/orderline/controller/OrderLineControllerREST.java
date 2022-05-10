package com.ezshopping.order.orderline.controller;

import com.ezshopping.order.orderline.service.OrderLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ezshopping.api.EndpointsAPI.*;

@RestController
@RequestMapping(API + ORDER_LINES)
@Slf4j
public class OrderLineControllerREST {

    private final OrderLineService orderLineService;

    public OrderLineControllerREST(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @DeleteMapping("/delete/ol/{id}")
    public ResponseEntity<Void> deleteOrderLine(@PathVariable(name = "id") String orderLineId) {
        this.orderLineService.removeOrderLine(orderLineId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
