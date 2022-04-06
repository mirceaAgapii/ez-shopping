package com.ezshopping.arduino.model.dto;

import com.ezshopping.order.orderline.model.dto.OrderLineDTO;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
public class RfScanDTO {
    public String userId;
    public String orderId;
    public List<OrderLineDTO> orderLines;
    public OrderLineDTO orderLineDTO;
    public double totalQty;
    public boolean finished;
}
