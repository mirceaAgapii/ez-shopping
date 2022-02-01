package com.ezshopping.fixture;

import com.ezshopping.location.cart.model.dto.CartDTO;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CartDTOFixture {

    private static final CartDTO cartDTO;

    static {
        cartDTO = CartDTO.builder()
                .id("testId")
                .productList(new HashSet<>(ProductDTOFixture.productDTOList()))
                .user(UserDTOFixture.userDTO())
                .build();
    }

    public static CartDTO cartDTO() {
        return cartDTO;
    }

    public static List<CartDTO> cartDTOList() {
        return Arrays.asList(cartDTO);
    }
}
