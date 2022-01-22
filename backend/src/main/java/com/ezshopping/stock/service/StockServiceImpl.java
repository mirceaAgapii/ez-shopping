package com.ezshopping.stock.service;

import com.ezshopping.location.exceptions.LocationNotFoundException;
import com.ezshopping.location.model.Location;
import com.ezshopping.location.repository.LocationRepository;
import com.ezshopping.location.service.LocationService;
import com.ezshopping.product.model.Product;
import com.ezshopping.product.repository.ProductRepository;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.stock.exceptions.StockAlreadyExistsException;
import com.ezshopping.stock.exceptions.StockNotFoundException;
import com.ezshopping.stock.model.StockDTO;
import com.ezshopping.stock.model.Stock;
import com.ezshopping.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Service
class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductService productService;
    private final LocationService locationService;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository,
                            ProductService productService,
                            LocationService locationService) {
        this.stockRepository = stockRepository;
        this.productService = productService;
        this.locationService = locationService;
    }

    @Override
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @Override
    public List<Stock> getAllByProduct(Product product) {
        return stockRepository.findAllByProduct(product)
                .orElseThrow(() -> new StockNotFoundException("No stock found with requested product [" + product.getId() + "]"));
    }

    @Override
    public Stock getStockById(String id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock for id [" + id + "] could not be found"));
    }

    @Override
    @Transactional
    public void createStock(StockDTO stockDTO) throws StockAlreadyExistsException {
        Location location = locationService.getById(stockDTO.getLocation().getId());
        Product product = productService.getById(stockDTO.getProduct().getId());

        if (stockRepository.existsByLocationAndProduct(location, product)) {
            throw new StockAlreadyExistsException("In location [" + stockDTO.getLocation().getId()
                    + "] is already present stock for product [" + stockDTO.getProduct().getId() + "]");
        }
        Stock newStock = Stock.builder()
                .location(location)
                .product(product)
                .locationType(stockDTO.getLocationType())
                .quantity(stockDTO.getQuantity())
                .build();
        newStock.setId(UUID.randomUUID().toString());
        stockRepository.save(newStock);
    }

    @Override
    @Transactional
    public void updateStock(StockDTO stockDTO) {
        Location location = locationService.getById(stockDTO.getLocation().getId());
        Product product = productService.getById(stockDTO.getProduct().getId());

        Stock stock = getStockById(stockDTO.getId());
        stock.setLocation(location);
        stock.setLocationType(stockDTO.getLocationType());
        stock.setProduct(product);
    }

    @Override
    @Transactional
    public void deleteStock(String id) {
        Stock stock = getStockById(id);
        stockRepository.delete(stock);
    }
}
