package com.ezshopping.product.repository;

import com.ezshopping.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByNameOrBarcodeOrRfId(String name, String barcode, String rfId);
    Optional<Product> findByBarcode(String barcode);
    Optional<Product> findByRfId(String rfId);

    @Query("select max(product.id) " +
            "from Product product")
    int getMaxId();

    List<Product> findAllByStatus(String status);
}
