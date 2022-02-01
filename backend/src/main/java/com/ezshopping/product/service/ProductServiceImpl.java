package com.ezshopping.product.service;

import com.ezshopping.common.Mapper;
import com.ezshopping.product.exceptions.ProductAlreadyInDatabaseException;
import com.ezshopping.product.exceptions.ProductNotFoundException;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.model.dto.ProductDTO;
import com.ezshopping.product.repository.ProductRepository;
import com.ezshopping.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Mapper<Product, ProductDTO> mapper;

    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductByBarcode(String barcode) {
        Product product = productRepository
                .findByBarcode(barcode)
                .orElseThrow(() -> new ProductNotFoundException("Product with provided barcode [" + barcode + "] doesn't exist"));
        return mapper.map(product);
    }

    @Override
    public Product getById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id [" + id + "] doesn't exist"));
    }

    @Override
    public ProductDTO getProductDTOByRfId(String rfId) {
        return mapper.map(getProductByRfId(rfId));
    }

    @Override
    public Product getProductByRfId(String rfId) {
        return productRepository
                .findByRfId(rfId)
                .orElseThrow(() -> new ProductNotFoundException("Product with provided rfId [" + rfId + "] doesn't exist"));
    }

    @Override
    @Transactional
    public void saveProduct(ProductDTO productDTO) {
        if(productRepository.existsByNameOrBarcodeOrRfId(
                productDTO.getName(),
                productDTO.getBarcode(),
                productDTO.getRfId())) {
            throw new ProductAlreadyInDatabaseException("Product is already in database");
        }
            Product newProduct = Product
                    .builder()
                    .name(productDTO.getName())
                    .description(productDTO.getDescription())
                    .price(productDTO.getPrice())
                    .barcode(productDTO.getBarcode())
                    .status(productDTO.getStatus())
                    .category(productDTO.getCategory())
                    .brand(productDTO.getBrand())
                    .rfId(productDTO.getRfId())
                    .build();
            newProduct.setId(Utilities.getNewUuid());
            productRepository.save(newProduct);
    }

    @Override
    @Transactional
    public void updateProduct(ProductDTO productDTO) {
        Product product = getById(productDTO.getId());
        product.setBarcode(productDTO.getBarcode());
        product.setBrand(productDTO.getBrand());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getName());
        product.setRfId(productDTO.getRfId());
        product.setStatus(productDTO.getStatus());

        productRepository.save(product);
    }

    @Override
    public ProductDTO getProductDTOById(String id) {
        Product product = getById(id);
        return mapper.map(product);
    }
}
