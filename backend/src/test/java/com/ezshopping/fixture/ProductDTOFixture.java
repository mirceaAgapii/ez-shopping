package com.ezshopping.fixture;

import com.ezshopping.product.model.dto.ProductDTO;

import java.util.Arrays;
import java.util.List;

public class ProductDTOFixture {

    private static final ProductDTO productDTO1;
    private static final ProductDTO productDTO2;

    static {
        productDTO1 = ProductDTO.builder()
                .id("testId1")
                .name("TestProductDTO")
                .barcode("123456789")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();

        productDTO2 = ProductDTO.builder()
                .id("testId2")
                .name("TestProductDTO")
                .barcode("123456789")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();
    }

    public static ProductDTO productDTO() {
        return productDTO1;
    }

    public static List<ProductDTO> productDTOList() {
        return Arrays.asList(productDTO1, productDTO2);
    }
}
