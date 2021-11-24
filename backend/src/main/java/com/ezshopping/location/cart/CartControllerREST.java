package com.ezshopping.location.cart;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.model.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointsAPI.API + EndpointsAPI.CARTS)
@Slf4j
public class CartControllerREST extends AbstractController {
    @Override
    public ResponseEntity<List> getAllEntities() {
        return null;
    }
}
