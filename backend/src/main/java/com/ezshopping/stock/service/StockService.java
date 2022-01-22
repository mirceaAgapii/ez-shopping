package com.ezshopping.stock.service;

import com.ezshopping.product.model.Product;
import com.ezshopping.stock.model.StockDTO;
import com.ezshopping.stock.model.Stock;

import java.util.List;

public interface StockService {
    List<Stock> getAll();
    List<Stock> getAllByProduct(Product product);
    Stock getStockById(String id);
    void createStock(StockDTO stockDTO);
    void updateStock(StockDTO stockDTO);
    void deleteStock(String id);
}
