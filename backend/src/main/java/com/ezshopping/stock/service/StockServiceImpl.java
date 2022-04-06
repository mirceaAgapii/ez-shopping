package com.ezshopping.stock.service;

import com.ezshopping.common.Mapper;
import com.ezshopping.location.model.entity.Location;
import com.ezshopping.location.service.LocationService;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.service.ProductService;
import com.ezshopping.stock.exceptions.StockAlreadyExistsException;
import com.ezshopping.stock.exceptions.StockNotFoundException;
import com.ezshopping.stock.model.dto.StockDTO;
import com.ezshopping.stock.model.entity.Stock;
import com.ezshopping.stock.repository.StockRepository;
import com.ezshopping.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductService productService;
    private final LocationService locationService;
    private final Mapper<Stock, StockDTO> mapper;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository,
                            ProductService productService,
                            LocationService locationService,
                            Mapper<Stock, StockDTO> mapper) {
        this.stockRepository = stockRepository;
        this.productService = productService;
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @Override
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @Override
    public List<StockDTO> getAllAsDTO() {
        List<Stock> all = getAll();
        return all.stream().map(mapper::map).collect(Collectors.toList());
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
    public StockDTO getStockDTOById(String id) {
        Stock stock = getStockById(id);
        return mapper.map(stock);
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
        newStock.setId(Utilities.getNewUuid());
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

        stockRepository.save(stock);
    }

    @Override
    @Transactional
    public void deleteStock(String id) {
        Stock stock = getStockById(id);
        stockRepository.delete(stock);
    }
}
