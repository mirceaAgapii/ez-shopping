package com.ezshopping.order.service;

import com.ezshopping.order.exception.OrderNotFoundException;
import com.ezshopping.order.model.entity.Order;
import com.ezshopping.order.repository.OrderRepository;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.service.UserService;
import com.ezshopping.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    @Transactional
    public Order createAnOrderForUser(String userId) {
        User user = userService.getUserById(userId);
        Order order = new Order();
        order.setTotal(0.00);
        order.setUser(user);
        order.setId(Utilities.getNewUuid());
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order findByUserId(String userId) {
        return orderRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderNotFoundException("Order for user with id " + userId + " could not be found."));
    }

    @Override
    public Order checkActiveOrderForUser(String userId) {
        Optional<Order> optionalOrder = orderRepository.findByUserId(userId);
        if (optionalOrder.isEmpty() || optionalOrder.get().isFinished()) {
            return createAnOrderForUser(userId);
        } else {
          return optionalOrder.get();
        }
    }

    @Override
    @Transactional
    public void updateTotalQty(Order order) {
        if(Objects.isNull(order.getTotal())) {
            order.setTotal(0.00);
        }
        order.setTotal(order.getTotal() + 1.00);
    }

    @Override
    @Transactional
    public void finishOrderById(String orderId) {
        Order order = getById(orderId);
        order.setFinished(true);
    }

    @Override
    public Order getById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id [" + orderId + "] couldn't be found"));
    }
}
