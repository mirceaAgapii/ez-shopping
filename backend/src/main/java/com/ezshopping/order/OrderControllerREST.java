package com.ezshopping.order;

import com.ezshopping.model.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.ezshopping.api.EndpointsAPI.*;

@RestController
@RequestMapping(API + ORDERS)
@Slf4j
public class OrderControllerREST extends AbstractController<OrderEntity> {
    @Override
    public ResponseEntity getAllEntities() {
        return null;
    }
}
