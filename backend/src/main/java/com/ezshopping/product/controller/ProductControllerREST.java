package com.ezshopping.product.controller;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.product.model.ProductDTO;
import com.ezshopping.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointsAPI.API + EndpointsAPI.PRODUCTS)
@Slf4j
public class ProductControllerREST {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllEntities() {
        return ResponseEntity.ok().body(productService.getAll());
    }
}
