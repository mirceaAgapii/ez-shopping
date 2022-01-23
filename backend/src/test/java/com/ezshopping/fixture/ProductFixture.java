package com.ezshopping.fixture;

import com.ezshopping.product.model.entity.Product;

import java.util.Arrays;
import java.util.List;

public class ProductFixture {

    private static final Product product1;
    private static final Product product2;

    static {
        product1 = Product.builder()
                .name("TestProduct1")
                .barcode("123456789")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();
        product1.setId("testId1");

        product2 = Product.builder()
                .name("TestProduct2")
                .barcode("987654321")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();
        product2.setId("testId2");
    }

    public static Product product() {
        return product1;
    }

    public static List<Product> productList() {
        return Arrays.asList(product1, product2);
    }
}
