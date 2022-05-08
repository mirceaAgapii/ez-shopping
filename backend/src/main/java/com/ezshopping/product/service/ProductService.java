package com.ezshopping.product.service;

import com.ezshopping.product.model.entity.Product;
import com.ezshopping.product.model.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<String> getAllProductNames();

    List<ProductDTO> getAll();
    List<ProductDTO> getAllPromo();

    Product getById(String id);

    ProductDTO getProductByBarcode(String barcode);

    Product getProductByRfId(String rfId);

    ProductDTO saveProduct(ProductDTO productDTO);

    @Transactional
    void setProductImage(String id, MultipartFile file);

    void updateProduct(ProductDTO productDTO);

    ProductDTO getProductDTOById(String id);

    ProductDTO getProductDTOByRfId(String rfId);
}
