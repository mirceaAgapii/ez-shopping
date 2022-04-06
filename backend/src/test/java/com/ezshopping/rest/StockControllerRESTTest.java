package com.ezshopping.rest;

import com.ezshopping.common.Mapper;
import com.ezshopping.fixture.LocationFixture;
import com.ezshopping.fixture.ProductFixture;
import com.ezshopping.fixture.StockDTOFixture;
import com.ezshopping.fixture.StockFixture;
import com.ezshopping.location.service.LocationServiceImpl;
import com.ezshopping.product.service.ProductServiceImpl;
import com.ezshopping.stock.model.dto.StockDTO;
import com.ezshopping.stock.model.entity.Stock;
import com.ezshopping.stock.service.StockServiceImpl;
import com.ezshopping.user.service.UserServiceImpl;
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
class StockControllerRESTTest {

    @MockBean
    private StockServiceImpl stockService;
    @MockBean
    private Mapper<Stock, StockDTO> mapper;
    @MockBean
    private LocationServiceImpl locationService;
    @MockBean
    private ProductServiceImpl productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void getAllEntities_whenInvoked_return200() throws Exception {
        mockMvc.perform(get("/api/stock")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void getStockById_whenInvoked_return200() throws Exception {
        when(stockService.getStockById(any(String.class))).thenReturn(StockFixture.stock());
        when(mapper.map(StockFixture.stock())).thenReturn(StockDTOFixture.stockDTO());
        mockMvc.perform(get("/api/stock/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void addStock_whenInvoked_return201() throws Exception {
        when(locationService.getById(any(String.class))).thenReturn(LocationFixture.locationStore());
        when(productService.getById(any(String.class))).thenReturn(ProductFixture.product());

        String body = "{\n" +
                "    \"locationType\" : \"STORE\",\n" +
                "    \"location\" : {\n" +
                "        \"id\": \"1\"\n" +
                "    },\n" +
                "    \"product\":{\n" +
                "        \"id\": \"1\"\n" +
                "    },\n" +
                "    \"quantity\":10\n" +
                "}";

        mockMvc.perform(post("/api/stock")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}