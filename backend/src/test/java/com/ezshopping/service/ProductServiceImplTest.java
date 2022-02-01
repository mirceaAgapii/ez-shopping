package com.ezshopping.service;

import com.ezshopping.common.Mapper;
import com.ezshopping.fixture.ProductDTOFixture;
import com.ezshopping.fixture.ProductFixture;
import com.ezshopping.product.exceptions.ProductNotFoundException;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.model.dto.ProductDTO;
import com.ezshopping.product.repository.ProductRepository;
import com.ezshopping.product.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private Mapper<Product, ProductDTO> mapper;
    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct1;
    private Product testProduct2;
    private ProductDTO testProductDTO;
    private List<Product> products;

    @BeforeEach
    void setup() {
        testProduct1 = ProductFixture.product();
        testProductDTO = ProductDTOFixture.productDTO();
        products = ProductFixture.productList();

    }

    @Test
    void getAll_whenInvoked_willReturnAListOfProducts() {
        when(productRepository.findAll()).thenReturn(products);

        assertThat(productService.getAll()).isNotEmpty();
    }

    @Test
    void getProductByBarcode_whenInvokedWithCorrectBarcode_willReturnAValidDTO() {
        when(productRepository.findByBarcode("123456789")).thenReturn(Optional.ofNullable(testProduct1));
        when(mapper.map(testProduct1)).thenReturn(testProductDTO);

        assertThat(productService.getProductByBarcode("123456789")).isEqualTo(testProductDTO);
    }

    @Test
    void getProductByBarcode_whenInvokedWithUnknownBarcode_willThrowProductNotFoundException() {
        when(productRepository.findByBarcode("0000000001")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductByBarcode("0000000001"));
    }

    @Test
    void getProductByRfId_whenInvokedWithCorrectRfID_willReturnAValidDTO() {
        when(productRepository.findByRfId("10 20 30 40")).thenReturn(Optional.ofNullable(testProduct1));
        when(mapper.map(testProduct1)).thenReturn(testProductDTO);

        assertThat(productService.getProductDTOByRfId("10 20 30 40")).isEqualTo(testProductDTO);
    }

}