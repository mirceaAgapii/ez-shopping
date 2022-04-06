package com.ezshopping.product.model.dto;

import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class ProductDTO {

    private String id;

    private String name;

    private String description;

    private Double price;

    private String barcode;

    private String status;

    private String category;

    private String brand;

    private String rfId;
}
