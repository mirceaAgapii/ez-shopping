package com.ezshopping.product.repository;

import com.ezshopping.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByNameOrBarcodeOrRfId(String name, String barcode, String rfId);
}
