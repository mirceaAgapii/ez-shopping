package com.ezshopping.location.cart.mapper;

import com.ezshopping.common.Mapper;
import com.ezshopping.location.cart.model.entity.Cart;
import com.ezshopping.location.cart.model.dto.CartDTO;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class CartDTOMapper implements Mapper<Cart, CartDTO> {

    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartDTOMapper(UserService userService,
                         ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }
    @Override
    public CartDTO map(Cart entity) {
        return CartDTO.builder()
                .id(entity.getId())
                .user(userService.getUserDTOById(entity.getUser().getId()))
                .productList(entity.getProductList().stream().map(p -> productService.getProductDTOById(p.getId())).collect(Collectors.toSet()))
                .build();
    }
}
