package com.ezshopping.order.service;

import com.ezshopping.order.model.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> getAll();
}
