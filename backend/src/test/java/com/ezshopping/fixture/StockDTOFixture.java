package com.ezshopping.fixture;

import com.ezshopping.stock.model.dto.StockDTO;

public class StockDTOFixture {

    private static final StockDTO stockDTO;

    static {
        stockDTO = StockDTO.builder()
                .id("testId")
                .location(LocationDTOFixture.locationDTO())
                .locationType("STORE")
                .product(ProductDTOFixture.productDTO())
                .quantity(10.00)
                .build();
    }

    public static StockDTO stockDTO() {
        return stockDTO;
    }
}
