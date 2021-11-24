package com.ezshopping.stock;

import com.ezshopping.model.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServ extends AbstractService<StockEntity> {
    @Override
    public List getAll() {
        return null;
    }
}
