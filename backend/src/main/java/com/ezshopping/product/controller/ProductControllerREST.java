package com.ezshopping.product.controller;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.product.model.dto.ProductDTO;
import com.ezshopping.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping(EndpointsAPI.API + EndpointsAPI.PRODUCTS)
@Slf4j
public class ProductControllerREST {

    private ProductService productService;

    @Autowired
    public ProductControllerREST(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllEntities() {
        log.info("getAllEntities: received GET request");
        return ResponseEntity.ok().body(productService.getAll());
    }

    @GetMapping("/product")
    public ResponseEntity<ProductDTO> getProductByBarcodeOrRfId(@RequestParam(name = "barcode", required = false) String barcode,
                                                                @RequestParam(name = "rfId", required = false) String rfId) {
        if(nonNull(barcode)){
            log.info("getProductByBarcodeOrRfId: received GET request for barcode [{}]", barcode);
            return ResponseEntity.ok().body(productService.getProductByBarcode(barcode));
        } else if(nonNull(rfId)) {
            log.info("getProductByBarcodeOrRfId: received GET request for rfId [{}]", rfId);
            return ResponseEntity.ok().body(productService.getProductDTOByRfId(rfId));
        } else {
            throw new NoSuchParameterException("getProductByBarcodeOrRfId: no params provided");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDTO productDTO) {
        log.info("saveProduct: received POST request for product [{}]", productDTO.getName());
        productService.saveProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
