package com.ezshopping.product.mapper;

import com.ezshopping.common.Mapper;
import com.ezshopping.product.model.dto.ShoppingListDTO;
import com.ezshopping.product.model.entity.ShoppingListItem;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListMapper implements Mapper<ShoppingListItem, ShoppingListDTO> {
    @Override
    public ShoppingListDTO map(ShoppingListItem entity) {
        return ShoppingListDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .productName(entity.getProductName())
                .active(entity.isActive())
                .build();
    }
}
