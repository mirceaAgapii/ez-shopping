package com.ezshopping.location.cart;

import com.ezshopping.model.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService extends AbstractService<CartEntity> {

    @Override
    public List<CartEntity> getAll() {
        return null;
    }
}
