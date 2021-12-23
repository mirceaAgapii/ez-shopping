package com.ezshopping.product.model;

import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class ProductDTO {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private String barcode;
}
