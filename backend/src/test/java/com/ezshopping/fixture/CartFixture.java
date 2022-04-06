package com.ezshopping.fixture;

import com.ezshopping.location.cart.model.entity.Cart;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CartFixture {

    private static final Cart cart;

    static {
        cart = Cart.builder()
                .productList(new HashSet<>(ProductFixture.productList()))
                .user(UserFixture.user())
                .build();
        cart.setId("testId");
    }

    public static Cart cart() {
        return cart;
    }

    public static List<Cart> cartList() {
        return Arrays.asList(cart);
    }
}
