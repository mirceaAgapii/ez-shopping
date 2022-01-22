package com.ezshopping.stock.controller;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.stock.model.entity.Stock;
import com.ezshopping.stock.model.dto.StockDTO;
import com.ezshopping.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointsAPI.API + EndpointsAPI.STOCK)
@Slf4j
public class StockControllerREST  {

    private final StockService stockService;

    @Autowired
    public StockControllerREST(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllEntities() {
        log.info("getAllEntities: received a GET request");
        return ResponseEntity.ok().body(stockService.getAllAsDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable("id") String id) {
        log.info("getStockById: received a GET request for id [{}]", id);
        return ResponseEntity.ok().body(stockService.getStockDTOById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addStock(@RequestBody StockDTO stockDTO) {
        log.info("addStock: received a POST request ");
        stockService.createStock(stockDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
