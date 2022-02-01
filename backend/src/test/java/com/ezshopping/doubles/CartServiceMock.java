package com.ezshopping.doubles;

import com.ezshopping.fixture.CartFixture;
import com.ezshopping.location.cart.model.dto.CartDTO;
import com.ezshopping.location.cart.model.entity.Cart;
import com.ezshopping.location.cart.service.CartService;

import java.util.List;

public class CartServiceMock implements CartService {

    private CartRepositoryMock cartRepository;

    public CartServiceMock(CartRepositoryMock cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> getAll() {
        return null;
    }

    @Override
    public Cart getById(String id) {
        return null;
    }

    @Override
    public void createCart(CartDTO cartDTO) {
        Cart cart = CartFixture.cart();
        cartRepository.save(cart);
    }

    @Override
    public void updateCart(CartDTO cartDTO) {

    }

    @Override
    public void deleteCart(String id) {

    }
}
