package com.ezshopping.order.service;

import com.ezshopping.order.model.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order createAnOrderForUser(String data);

    Order findByUserId(String userId);

    Order checkActiveOrderForUser(String userId);

    void updateTotalQty(Order order);

    void finishOrderById(String orderId);

    Order getById(String orderId);
}
