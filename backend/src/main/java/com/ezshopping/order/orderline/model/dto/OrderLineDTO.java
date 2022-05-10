package com.ezshopping.order.orderline.model.dto;

import lombok.Builder;

@Builder
public class OrderLineDTO {
    public String orderLineId;
    public String productName;
    public String productId;
    public String productImageUrl;
    public double price;
    public String productDescr;
}
