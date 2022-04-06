package com.ezshopping.service;

import com.ezshopping.fixture.*;
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
    private StockDTO testStockDTO;
    private Product testProduct1;
    private ProductDTO productDTO;
    private Location testLocationStore;
    private LocationDTO locationDTO;

    private List<Stock> stockList;
    @BeforeEach
    void setup() {
        testLocationStore = LocationFixture.locationStore();
        locationDTO = LocationDTOFixture.locationDTO();
        productDTO = ProductDTOFixture.productDTO();

        testProduct1 = ProductFixture.product();
        testStock1 = StockFixture.stock();

        stockList = StockFixture.stockList();

        testStockDTO = StockDTOFixture.stockDTO();
    }

    @Test
    void getAll_whenInvoked_willReturnAListOfStock() {
        when(stockRepository.findAll()).thenReturn(stockList);

        assertThat(stockService.getAll()).isNotEmpty();
    }

    @Test
    void getAllByProduct_whenInvokedWithAKnownProduct_returnsAListOfStockEntities() {
        when(stockRepository.findAllByProduct(testProduct1)).thenReturn(Optional.of(stockList));

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

    //@Test
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