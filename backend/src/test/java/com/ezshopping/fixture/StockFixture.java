package com.ezshopping.fixture;

import com.ezshopping.location.model.entity.Location;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.stock.model.entity.Stock;

import java.util.Arrays;
import java.util.List;

public class StockFixture {

    private static final Stock stockStore;
    private static final Stock stockCart;

    static {
        stockStore = Stock.builder()
                .product(ProductFixture.product())
                .location(LocationFixture.locationStore())
                .locationType("STORE")
                .quantity(10.00)
                .build();
        stockStore.setId("testId");

        stockCart = Stock.builder()
                .product(ProductFixture.product())
                .location(LocationFixture.locationStore())
                .locationType("CART")
                .quantity(2.00)
                .build();
        stockCart.setId("testId");
    }

    public static Stock stock() {
        return stockStore;
    }

    public static List<Stock> stockList() {
        return Arrays.asList(stockStore, stockCart);
    }
}
