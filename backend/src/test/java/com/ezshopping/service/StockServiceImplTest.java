package com.ezshopping.service;

import com.ezshopping.location.model.entity.Location;
import com.ezshopping.location.model.dto.LocationDTO;
import com.ezshopping.location.service.LocationService;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.model.dto.ProductDTO;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.stock.exceptions.StockNotFoundException;
import com.ezshopping.stock.model.entity.Stock;
import com.ezshopping.stock.model.dto.StockDTO;
import com.ezshopping.stock.repository.StockRepository;
import com.ezshopping.stock.service.StockServiceImpl;
import com.ezshopping.util.Utilities;
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
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;
    @Mock
    private ProductService productService;
    @Mock
    private LocationService locationService;
    @Mock
    private Utilities utilities;
    @InjectMocks
    private StockServiceImpl stockService;

    private Stock testStock1;
    private Stock testStock2;
    private StockDTO testStockDTO;
    private Product testProduct1;
    private ProductDTO productDTO;
    private Location testLocationStore;
    private Location testLocationCart;
    private LocationDTO locationDTO;

    private List<Stock> stockList;
    @BeforeEach
    void setup() {
        testLocationStore = new Location();
        testLocationStore.setId("testId");
        testLocationStore.setLocationType("STORE");

        testLocationCart = new Location();
        testLocationCart.setId("testId2");
        testLocationCart.setLocationType("CART");

        locationDTO = LocationDTO.builder()
                .locationType("STORE")
                .id("testId")
                .build();

        productDTO = ProductDTO.builder()
                .name("TestProductDTO")
                .barcode("123456789")
                .brand("TestBrand")
                .category("TestCategory")
                .description("TestDescription")
                .price(100.00)
                .rfId("10 20 30 40")
                .status("IN STOCK")
                .build();

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
        testStock1 = Stock.builder()
                .product(testProduct1)
                .location(testLocationStore)
                .locationType("STORE")
                .quantity(10.00)
                .build();
        testStock1.setId("testId");
        testStock2 = Stock.builder()
                .product(testProduct1)
                .location(testLocationCart)
                .locationType("CART")
                .quantity(2.00)
                .build();
        testStock2.setId("testId");

        stockList = Arrays.asList(testStock1, testStock2);

        testStockDTO = StockDTO.builder()
                .id("testId")
                .location(locationDTO)
                .locationType("STORE")
                .product(productDTO)
                .quantity(10.00)
                .build();
    }

    @Test
    void getAll_whenInvoked_willReturnAListOfStock() {
        when(stockRepository.findAll()).thenReturn(stockList);

        assertThat(stockService.getAll()).isNotEmpty();
    }

    @Test
    void getAllByProduct_whenInvokedWithAKnownProduct_returnsAListOfStockEntities() {
        when(stockRepository.findAllByProduct(testProduct1)).thenReturn(Optional.of(Arrays.asList(testStock1, testStock2)));

        assertThat(stockService.getAllByProduct(testProduct1)).isEqualTo(stockList);
    }

    @Test
    void getAllByProduct_whenInvokedWithAUnknownProduct_thrownStockNotFoundException() {
        when(stockRepository.findAllByProduct(any(Product.class))).thenReturn(Optional.empty());

        assertThrows(StockNotFoundException.class, () -> stockService.getAllByProduct(new Product()));
    }

    @Test
    void getStockById_whenInvokedWithAnExistingId_returnsAStockEntity() {
        when(stockRepository.findById("testId")).thenReturn(Optional.ofNullable(testStock1));

        assertThat(stockService.getStockById("testId")).isEqualTo(testStock1);
    }

    @Test
    void getStockById_whenInvokedWithAnUnknownId_thrownStockNotFoundException() {
        when(stockRepository.findById("unknownId")).thenReturn(Optional.empty());

        assertThrows(StockNotFoundException.class, () -> stockService.getStockById("unknownId"));
    }

    @Test
    void createStock_whenInvoked_callSaveOnce() {
        when(locationService.getById(locationDTO.getId())).thenReturn(testLocationStore);
        when(productService.getById(productDTO.getId())).thenReturn(testProduct1);
        when(utilities.getNewUuid()).thenReturn("testId");

        stockService.createStock(testStockDTO);
        verify(stockRepository, times(1)).save(testStock1);
    }

    @Test
    void updateStock_whenInvoked_callSaveOnce() {
        when(locationService.getById(locationDTO.getId())).thenReturn(testLocationStore);
        when(productService.getById(productDTO.getId())).thenReturn(testProduct1);
        when(stockRepository.findById(testStockDTO.getId())).thenReturn(Optional.ofNullable(testStock1));

        stockService.updateStock(testStockDTO);
        verify(stockRepository, times(1)).save(testStock1);
    }

}