package com.ezshopping.order.repository;

import com.ezshopping.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(nativeQuery = true, value = "select * from ez_order  where user_id = :userId")
    Optional<Order> findByUserId(@Param("userId") String userId);
}
