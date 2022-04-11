package com.ezshopping.order.repository;

import com.ezshopping.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByUserIdAndFinished(String userId, boolean finished);
}
