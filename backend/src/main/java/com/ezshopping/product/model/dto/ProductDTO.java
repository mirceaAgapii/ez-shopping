package com.ezshopping.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
