package com.ezshopping.stock;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.model.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointsAPI.API + EndpointsAPI.STOCK)
@Slf4j
public class StockControllerREST extends AbstractController<StockEntity> {
    @Override
    public ResponseEntity<List<StockEntity>> getAllEntities() {
        return null;
    }
}
