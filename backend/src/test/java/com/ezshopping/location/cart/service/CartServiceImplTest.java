package com.ezshopping.location.cart.service;

import com.ezshopping.location.cart.exceptions.CartNotFoundException;
import com.ezshopping.location.cart.model.Cart;
import com.ezshopping.location.cart.model.CartDTO;
import com.ezshopping.location.cart.repository.CartRepository;
import com.ezshopping.product.model.Product;
import com.ezshopping.product.model.ProductDTO;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.user.model.User;
import com.ezshopping.user.model.UserDTO;
import com.ezshopping.user.service.UserService;
import com.ezshopping.util.Utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
    private ProductDTO testProductDTO1;
    private ProductDTO testProductDTO2;
    private User testUser1;
    private UserDTO testUserDTO;
    private List<Cart> cartList;

    @BeforeEach
    void setup() {
        testProduct1 = Product.builder()
                .name("TestProduct1")
                .barcode("123456789")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();
        testProduct1.setId("testId1");
        testProduct2 = Product.builder()
                .name("TestProduct2")
                .barcode("987654321")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();
        testProduct2.setId("testId1");

        testProductDTO1 = ProductDTO.builder()
                .id("testId1")
                .name("TestProduct1")
                .barcode("123456789")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();
        testProductDTO2 = ProductDTO.builder()
                .id("testId2")
                .name("TestProduct2")
                .barcode("987654321")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();

        testUser1 = new User();
        testUser1.setUsername("TestUser1");
        testUser1.setPassword("testPassword1");
        testUser1.setRole("CLIENT");
        testUser1.setEmail("test1@mail.com");
        testUser1.setId("testId1");

        testUserDTO = UserDTO.builder()
                .id("testId")
                .role("CLIENT")
                .username("TestUser1")
                .email("test1@mail.com")
                .password("testPassword1")
                .build();

        testCart = Cart.builder()
                .productList(new HashSet<>(Arrays.asList(testProduct1, testProduct2)))
                .user(testUser1)
                .build();
        testCart.setId("testId");

        cartDTO = CartDTO.builder()
                .id("testId")
                .productList(new HashSet<>(Arrays.asList(testProductDTO1, testProductDTO2)))
                .user(testUserDTO)
                .build();

        cartList = Arrays.asList(testCart);
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

    @Test
    void createCart_whenInvoked_callsSaveOnce() {
        when(userService.getUserByUsername(testUserDTO.getUsername())).thenReturn(testUser1);
        when(productService.getById(testProductDTO1.getId())).thenReturn(testProduct1);
        when(productService.getById(testProductDTO2.getId())).thenReturn(testProduct2);
        when(utilities.getNewUuid()).thenReturn("testId");

        cartService.createCart(cartDTO);
        verify(cartRepository, times(1)).save(testCart);
    }

}