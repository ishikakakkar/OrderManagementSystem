package com.example.order.management.system.controller;

import com.example.order.management.system.modal.Product;
import com.example.order.management.system.service.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductController.class)
//@WithMockUser
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

    }

    @Test
    void testAddProduct() throws Exception {
        Product mockProduct = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );

        String inputJson = this.mapToJson(mockProduct);
        String mockResponse = "Product Info added successfully";
        String uri = "/products";

        Mockito.when(productService.addProduct(Mockito.any(Product.class)))
                .thenReturn(mockResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mockResponse, response.getContentAsString());

    }

    @Test
    void testGetProductList() throws Exception {
        Product mockProduct = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );

        ArrayList<Product> mockProductList = new ArrayList<>();
        mockProductList.add(mockProduct);

        String uri = "/products";

        Mockito.when(productService.getProductList())
                .thenReturn(mockProductList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                uri).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJson(mockProductList);
        String outputJson = result.getResponse().getContentAsString();

        assertEquals(expectedJson, outputJson);
    }

    @Test
    void testGetProductById() throws Exception {
        Product mockProduct = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );

        String uri = "/products/10";

        Mockito.when(productService.getProductById(Mockito.any(Integer.class)))
                .thenReturn(mockProduct);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                uri).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJson(mockProduct);
        String outputJson = result.getResponse().getContentAsString();

        assertEquals(expectedJson, outputJson);
    }

    @Test
    void testUpdateProduct() throws Exception {
        String uri = "/products/10";
        Product mockProduct = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );
        String inputJson = this.mapToJson(mockProduct);

        Mockito.when(productService.updateProduct(Mockito.any(Integer.class), Mockito.any(Product.class)))
                .thenReturn(mockProduct);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(inputJson, response.getContentAsString());
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}