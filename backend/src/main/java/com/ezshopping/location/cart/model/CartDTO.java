package com.ezshopping.location.cart.model;

import com.ezshopping.product.model.ProductDTO;
import com.ezshopping.user.model.UserDTO;
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
