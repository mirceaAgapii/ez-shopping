package com.ezshopping.location.cart.service;

import com.ezshopping.location.cart.model.Cart;
import com.ezshopping.location.cart.model.CartDTO;

import java.util.List;
import java.util.Optional;

public interface CartService {
    List<Cart> getAll();
    Cart getById(String id);
    void createCart(CartDTO cartDTO);
    void updateCart(CartDTO cartDTO);
    void deleteCart(String id);
}
