package com.ezshopping.location.cart.service;

import com.ezshopping.location.cart.exceptions.CartNotFoundException;
import com.ezshopping.location.cart.model.entity.Cart;
import com.ezshopping.location.cart.model.dto.CartDTO;
import com.ezshopping.location.cart.repository.CartRepository;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.service.UserService;
import com.ezshopping.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           ProductService productService,
                           UserService userService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getById(String id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart with id [" + id + "] doesn't exist"));
    }

    @Override
    public void createCart(CartDTO cartDTO) {
        User user = userService.getUserByUsername(cartDTO.getUser().getUsername());
        Set<Product> products = new HashSet<>();
        cartDTO.getProductList().forEach(product -> products.add(productService.getById(product.getId())));
        Cart newCart = Cart.builder()
                .user(user)
                .productList(products)
                .build();
        newCart.setId(Utilities.getNewUuid());
        cartRepository.save(newCart);
    }

    @Override
    public void updateCart(CartDTO cartDTO) {
        Cart cart = getById(cartDTO.getId());
        User user = userService.getUserByUsername(cartDTO.getUser().getUsername());
        Set<Product> products = new HashSet<>();
        cartDTO.getProductList().forEach(product -> products.add(productService.getById(product.getId())));
        cart.setUser(user);
        cart.getProductList().addAll(products);
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(String id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart with id [" + id + "] doesn't exist"));
        cartRepository.delete(cart);
    }


}
