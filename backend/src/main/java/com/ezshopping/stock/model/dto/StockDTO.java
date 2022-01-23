package com.ezshopping.stock.model.dto;

import com.ezshopping.location.model.dto.LocationDTO;
import com.ezshopping.product.model.dto.ProductDTO;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
public class StockDTO {

    private String id;
    @NotNull
    private String locationType;
    @NotNull
    private LocationDTO location;
    @NotNull
    private ProductDTO product;
    @NotNull
    private Double quantity;
}