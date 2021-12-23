package com.ezshopping.product.mapper;

import com.ezshopping.common.Mapper;
import com.ezshopping.product.model.Product;
import com.ezshopping.product.model.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper implements Mapper<Product, ProductDTO> {
    @Override
    public ProductDTO map(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .barcode(entity.getBarcode())
                .build();
    }
}
