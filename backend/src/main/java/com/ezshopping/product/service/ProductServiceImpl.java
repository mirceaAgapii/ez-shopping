package com.ezshopping.product.service;

import com.ezshopping.common.Mapper;
import com.ezshopping.product.model.Product;
import com.ezshopping.product.model.ProductDTO;
import com.ezshopping.product.repository.ProductRepository;
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
    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void saveArticle(ProductDTO productDTO) {
        if(!productRepository.existsByNameOrBarcodeOrRfId(
                productDTO.getName(),
                productDTO.getBarcode(),
                productDTO.getRfId())) {
            Product newProduct = Product
                    .builder()
                    .name(productDTO.getName())
                    .description(productDTO.getDescription())
                    .price(productDTO.getPrice())
                    .quantity(productDTO.getQuantity())
                    .barcode(productDTO.getBarcode())
                    .status(productDTO.getStatus())
                    .category(productDTO.getCategory())
                    .brand(productDTO.getBrand())
                    .rfId(productDTO.getRfId())
                    .build();
            newProduct.setId(UUID.randomUUID().toString());
            productRepository.save(newProduct);
        }
    }
}
