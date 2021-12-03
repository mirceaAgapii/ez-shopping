package com.ezshopping.stock.service;

import com.ezshopping.model.AbstractService;
import com.ezshopping.stock.model.StockEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class StockServiceImpl implements StockService {
    @Override
    public List<StockEntity> getAll() {
        return null;
    }
}
