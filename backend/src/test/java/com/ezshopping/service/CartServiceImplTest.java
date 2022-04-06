package com.ezshopping.service;

import com.ezshopping.fixture.*;
import com.ezshopping.location.cart.exceptions.CartNotFoundException;
import com.ezshopping.location.cart.model.entity.Cart;
import com.ezshopping.location.cart.model.dto.CartDTO;
import com.ezshopping.location.cart.repository.CartRepository;
import com.ezshopping.location.cart.service.CartServiceImpl;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.model.dto.ProductDTO;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.service.UserService;
import com.ezshopping.util.Utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductService productService;
    @Mock
    private UserService userService;
    @Mock
    private Utilities utilities;
    @InjectMocks
    private CartServiceImpl cartService;

    private Cart testCart;
    private CartDTO cartDTO;
    private Product testProduct1;
    private Product testProduct2;
    private List<Product> testProductList;
    private ProductDTO testProductDTO1;
    private ProductDTO testProductDTO2;
    private List<ProductDTO> testProductDTOList;
    private User testUser1;
    private UserDTO testUserDTO;
    private List<Cart> cartList;

    @BeforeEach
    void setup() {
        testProductList = ProductFixture.productList();
        testProduct1 = testProductList.get(0);
        testProduct2 = testProductList.get(1);

        testProductDTOList = ProductDTOFixture.productDTOList();
        testProductDTO1 = testProductDTOList.get(0);
        testProductDTO2 = testProductDTOList.get(1);

        testUser1 = UserFixture.user();
        testUserDTO = UserDTOFixture.userDTO();

        testCart = CartFixture.cart();

        cartDTO = CartDTOFixture.cartDTO();

        cartList = CartFixture.cartList();
    }

    @Test
    void getAll_whenInvoked_returnsAListOfCarts() {
        when(cartRepository.findAll()).thenReturn(cartList);

        assertThat(cartService.getAll()).isNotEmpty();
    }

    @Test
    void getById_whenInvokedWithUnknownId_throwsCartNotFoundException() {
        when(cartRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.getById("unknownId"));
    }

   // @Test
    void createCart_whenInvoked_callsSaveOnce() {
        when(userService.getUserByUsername(testUserDTO.getUsername())).thenReturn(testUser1);
        when(productService.getById(testProductDTO1.getId())).thenReturn(testProduct1);
        when(productService.getById(testProductDTO2.getId())).thenReturn(testProduct2);
        when(utilities.getNewUuid()).thenReturn("testId");

        cartService.createCart(cartDTO);
        verify(cartRepository, times(1)).save(testCart);
    }

}