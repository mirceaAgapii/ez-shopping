package com.ezshopping.stock.service;

import com.ezshopping.product.model.entity.Product;
import com.ezshopping.stock.model.dto.StockDTO;
import com.ezshopping.stock.model.entity.Stock;

import java.util.List;

public interface StockService {
    List<Stock> getAll();

    List<StockDTO> getAllAsDTO();

    List<Stock> getAllByProduct(Product product);
    Stock getStockById(String id);

    StockDTO getStockDTOById(String id);

    void createStock(StockDTO stockDTO);
    void updateStock(StockDTO stockDTO);
    void deleteStock(String id);
}
