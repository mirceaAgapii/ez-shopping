package com.ezshopping.location.cart.model.dto;

import com.ezshopping.product.model.dto.ProductDTO;
import com.ezshopping.user.model.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class CartDTO {

    private String id;
    private UserDTO user;
    private Set<ProductDTO> productList;
}
