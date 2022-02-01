package com.ezshopping.order.orderline.mapper;

import com.ezshopping.common.Mapper;
import com.ezshopping.order.orderline.model.dto.OrderLineDTO;
import com.ezshopping.order.orderline.model.entity.OrderLine;
import org.springframework.stereotype.Component;

@Component
public class OrderLineDTOMapper implements Mapper<OrderLine, OrderLineDTO> {

    @Override
    public OrderLineDTO map(OrderLine entity) {
        return OrderLineDTO.builder()
                .orderLineId(entity.getId())
                .productId(entity.getProduct().getRfId())
                .productName(entity.getProduct().getName())
                .olQty(entity.getQuantity())
                .build();
    }
}
