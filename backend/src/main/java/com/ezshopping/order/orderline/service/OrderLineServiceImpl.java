package com.ezshopping.order.orderline.service;

import com.ezshopping.order.model.entity.Order;
import com.ezshopping.order.orderline.model.entity.OrderLine;
import com.ezshopping.order.orderline.repository.OrderLineRepository;
import com.ezshopping.order.service.OrderService;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderService orderService;

    @Autowired
    public OrderLineServiceImpl(OrderLineRepository orderLineRepository,
                                OrderService orderService) {
        this.orderLineRepository = orderLineRepository;
        this.orderService = orderService;
    }

    @Override
    public List<OrderLine> getAllByParent(Order order){
        return this.orderLineRepository.findAllByParent(order);
    }

    @Override
    @Transactional
    public OrderLine createUpdateOrderLineForOrder(Order order, Product product) {
        Optional<OrderLine> optionalOrderLine = orderLineRepository.findByParentAndProduct(order, product);
        OrderLine orderLine;
        if (optionalOrderLine.isEmpty()) {
             orderLine = OrderLine.builder()
                    .product(product)
                    .parent(order)
                    .quantity(1.00)
                    .build();
            orderLine.setId(Utilities.getNewUuid());
        } else {
            orderLine = optionalOrderLine.get();
            orderLine.incrementQty();
        }
        orderLineRepository.save(orderLine);
        orderService.updateTotalQty(order);
        return orderLine;
    }
}
