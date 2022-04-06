package com.ezshopping.product.repository;

import com.ezshopping.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByNameOrBarcodeOrRfId(String name, String barcode, String rfId);
    Optional<Product> findByBarcode(String barcode);
    Optional<Product> findByRfId(String rfId);
}
