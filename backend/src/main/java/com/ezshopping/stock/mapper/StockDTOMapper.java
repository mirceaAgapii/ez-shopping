package com.ezshopping.stock.mapper;

import com.ezshopping.common.Mapper;
import com.ezshopping.location.mapper.LocationDTOMapper;
import com.ezshopping.product.mapper.ProductDTOMapper;
import com.ezshopping.stock.model.dto.StockDTO;
import com.ezshopping.stock.model.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockDTOMapper implements Mapper<Stock, StockDTO> {
    private final LocationDTOMapper locationDTOMapper;
    private final ProductDTOMapper productDTOMapper;

    public StockDTOMapper(LocationDTOMapper locationDTOMapper,
                          ProductDTOMapper productDTOMapper) {
        this.locationDTOMapper = locationDTOMapper;
        this.productDTOMapper = productDTOMapper;
    }
    @Override
    public StockDTO map(Stock entity) {
        return StockDTO.builder()
                .id(entity.getId())
                .locationType(entity.getLocationType())
                .location(locationDTOMapper.map(entity.getLocation()))
                .product(productDTOMapper.map(entity.getProduct()))
                .quantity(entity.getQuantity())
                .build();
    }
}
