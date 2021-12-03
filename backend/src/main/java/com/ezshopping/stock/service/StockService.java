package com.ezshopping.stock.service;

import com.ezshopping.stock.model.StockEntity;

import java.util.List;

public interface StockService {
    List<StockEntity> getAll();
}
