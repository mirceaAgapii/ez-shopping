package com.ezshopping.product.service;

import com.ezshopping.product.model.Product;
import com.ezshopping.product.model.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();

    Product getById(String id);

    ProductDTO getProductByBarcode(String barcode);

    ProductDTO getProductByRfId(String rfId);

    void saveProduct(ProductDTO productDTO);

    void updateProduct(ProductDTO productDTO);

    ProductDTO getProductDTOById(String id);
}
