package com.ezshopping.order.orderline.service;

import com.ezshopping.order.model.entity.Order;
import com.ezshopping.order.orderline.model.entity.OrderLine;
import com.ezshopping.product.model.entity.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderLineService {
    OrderLine createUpdateOrderLineForOrder(Order order, Product product);

    List<OrderLine> getAllByParent(Order order);

    @Transactional
    void removeOrderLine(String orderLineId);
}
