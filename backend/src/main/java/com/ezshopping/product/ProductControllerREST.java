package com.ezshopping.product;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.model.AbstractController;
import lombok.RequiredArgsConstructor;
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
public class ProductControllerREST extends AbstractController {

    @Autowired
    private ProductService productService;

    @Override
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllEntities() {
        return ResponseEntity.ok().body(productService.getAll());
    }
}
