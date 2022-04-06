package com.ezshopping.location.cart.service;

import com.ezshopping.location.cart.model.entity.Cart;
import com.ezshopping.location.cart.model.dto.CartDTO;

import java.util.List;

public interface CartService {
    List<Cart> getAll();
    Cart getById(String id);
    void createCart(CartDTO cartDTO);
    void updateCart(CartDTO cartDTO);
    void deleteCart(String id);
}
