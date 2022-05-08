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
public class ShoppingListDTO {

    private String id;
    @NotNull
    private String userId;
    @NotNull
    private String productName;
    @NotNull
    private boolean active;
}
