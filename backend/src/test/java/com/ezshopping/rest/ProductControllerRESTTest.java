package com.ezshopping.rest;

import com.ezshopping.fixture.ProductDTOFixture;
import com.ezshopping.product.model.dto.ProductDTO;
import com.ezshopping.product.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerRESTTest {

    @MockBean
    private ProductServiceImpl productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private ProductDTO productDTO;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        productDTO = ProductDTOFixture.productDTO();
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void getAllEntities_whenInvoked_return200() throws Exception {
        mockMvc.perform(get("/api/products")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void getProductByBarcodeOrRfId_whenInvokedWithNonNullBarcodeAndNullRfId_return200() throws Exception {
        when(productService.getProductByBarcode(productDTO.getBarcode())).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/product")
                .param("barcode", "test"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void getProductByBarcodeOrRfId_whenInvokedWithNullBarcodeAndNonNullRfId_return200() throws Exception {
        when(productService.getProductDTOByRfId(productDTO.getRfId())).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/product")
                        .param("rfId", "test"))
                .andExpect(status().isOk());
    }

    @Test
    void saveProduct_whenInvoked_return201() throws Exception {
        doNothing().when(productService).saveProduct(productDTO);

        String body = "{\n" +
                "    \"name\" : \"test\",\n" +
                "    \"description\":\"test\",\n" +
                "    \"price\":20,\n" +
                "    \"barcode\":\"test\",\n" +
                "    \"status\":\"test\",\n" +
                "    \"category\":\"test\",\n" +
                "    \"brand\":\"test\",\n" +
                "    \"rfId\":\"test\"\n" +
                "}";

        mockMvc.perform(post("/api/products/save")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}