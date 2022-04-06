package com.ezshopping.order.orderline.repository;

import com.ezshopping.order.model.entity.Order;
import com.ezshopping.order.orderline.model.entity.OrderLine;
import com.ezshopping.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, String> {

    Optional<OrderLine> findByParentAndProduct(Order parent, Product product);

    List<OrderLine> findAllByParent(Order parent);
}
