package com.ezshopping.stock.controller;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.stock.model.StockEntity;
import com.ezshopping.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointsAPI.API + EndpointsAPI.STOCK)
@Slf4j
@RequiredArgsConstructor
public class StockControllerREST  {

    private final StockService stockService;


    public ResponseEntity<List<StockEntity>> getAllEntities() {
        return null;
    }
}
