package com.ezshopping.product.controller;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.product.model.dto.ProductDTO;
import com.ezshopping.product.model.dto.ShoppingListDTO;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.product.service.ShoppingListItemService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping(EndpointsAPI.API + EndpointsAPI.PRODUCTS)
@Slf4j
public class ProductControllerREST {

    private ProductService productService;
    private ShoppingListItemService shoppingListItemService;

    @Autowired
    public ProductControllerREST(ProductService productService,
                                 ShoppingListItemService shoppingListItemService) {
        this.productService = productService;
        this.shoppingListItemService = shoppingListItemService;
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

    @GetMapping("/promo")
    public ResponseEntity<List<ProductDTO>> getAllPromoProducts() {
        log.info("getAllPromoProducts: received GET request");
        return ResponseEntity.ok().body(productService.getAllPromo());
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllProductNames() {
        log.info("getAllProductNames: received GET request");
        return ResponseEntity.ok().body(productService.getAllProductNames());
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        log.info("saveProduct: received POST request for product [{}]", productDTO.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDTO));
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<Void> setProductImage(@PathVariable("id") String id,
                                                @RequestBody MultipartFile image) {
        log.info("received request");
        productService.setProductImage(id, image);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/shoppinglist")
    public ResponseEntity<Void> saveShoppingList(@RequestBody List<ShoppingListDTO> shoppingListDTOS) {
        shoppingListItemService.saveShoppingList(shoppingListDTOS);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/shoppinglist/{userId}")
    public ResponseEntity<List<ShoppingListDTO>> getShoppingListForUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(shoppingListItemService.getShoppingListForUser(userId));
    }

    @DeleteMapping("/shoppinglist/{id}")
    public ResponseEntity<List<ShoppingListDTO>> deleteShoppingListItemById(@PathVariable("id") String shoppingListItemId) {
        shoppingListItemService.deleteShoppingListItem(shoppingListItemId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/shoppingitem")
    public ResponseEntity<ShoppingListDTO> saveShoppingList(@RequestBody ShoppingListDTO shoppingListDTO) {
        ShoppingListDTO dto = shoppingListItemService.saveShoppingListItem(shoppingListDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PatchMapping("/shoppinglist/{id}")
    public ResponseEntity<Void> updateShoppingListItemActiveStatus(@PathVariable("id") String shoppingListItemId,
                                                                   @RequestParam(name = "isActive") boolean isActive) {
        shoppingListItemService.updateStatus(shoppingListItemId, isActive);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
