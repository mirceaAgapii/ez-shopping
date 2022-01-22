package com.ezshopping.stock.repository;

import com.ezshopping.location.model.Location;
import com.ezshopping.product.model.Product;
import com.ezshopping.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findById(String id);
    boolean existsByLocationAndProduct(Location location, Product product);
    Optional<List<Stock>> findAllByProduct(Product product);
}
