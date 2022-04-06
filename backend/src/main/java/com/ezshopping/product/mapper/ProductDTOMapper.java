package com.ezshopping.product.mapper;

import com.ezshopping.common.Mapper;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.model.dto.ProductDTO;
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
                .category(entity.getCategory())
                .status(entity.getStatus())
                .brand(entity.getBrand())
                .rfId(entity.getRfId())
                .build();
    }
}
