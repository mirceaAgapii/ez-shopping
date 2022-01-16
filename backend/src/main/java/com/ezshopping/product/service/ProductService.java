package com.ezshopping.product.service;

import com.ezshopping.product.model.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();

    ProductDTO getProductByBarcode(String barcode);

    ProductDTO getProductByRfId(String rfId);

    void saveArticle(ProductDTO productDTO);
}
