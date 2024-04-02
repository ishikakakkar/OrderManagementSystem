package com.example.order.management.system.controller;

import com.example.order.management.system.exception.OrderNotFoundException;
import com.example.order.management.system.exception.ProductAlreadyExistsException;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.modal.Product;
import com.example.order.management.system.service.OrderServiceImpl;
import com.example.order.management.system.service.ProductServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OrdersController.class)
class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();


    }

    @Test
    void testPlaceOrder() throws Exception {
        OrderItem orderItem1 = new OrderItem(
                100,
                2
        );
        OrderItem orderItem2 = new OrderItem(
                201,
                5
        );
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        Orders mockOrder = new Orders(10);
        mockOrder.setOrderItems(orderItemList);


        String inputJson = this.mapToJson(mockOrder);
//        System.out.println(inputJson);

        String mockResponse = "Order Placed successfully";

        String uri = "/orders";

        Mockito.when(orderService.placeOrder(Mockito.any(Orders.class)))
                .thenReturn(mockResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

//        System.out.println(requestBuilder);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

//        System.out.println(result);

        MockHttpServletResponse response = result.getResponse();

//        System.out.println(response);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mockResponse, response.getContentAsString());

    }

    @Test
    void testGetOrderList() throws Exception {
        OrderItem orderItem1 = new OrderItem(
                100,
                2
        );
        OrderItem orderItem2 = new OrderItem(
                201,
                5
        );
        OrderItem orderItem3 = new OrderItem(
                351,
                18
        );
        ArrayList<OrderItem> orderItemList1 = new ArrayList<>();
        orderItemList1.add(orderItem1);
        orderItemList1.add(orderItem2);

        ArrayList<OrderItem> orderItemList2 = new ArrayList<>();
        orderItemList2.add(orderItem2);
        orderItemList2.add(orderItem3);

        Orders orders1 = new Orders(10);
        orders1.setOrderItems(orderItemList1);
        Orders orders2 = new Orders(20);
        orders2.setOrderItems(orderItemList2);

        ArrayList<Orders> mockOrdersList = new ArrayList<>();
        mockOrdersList.add(orders1);
        mockOrdersList.add(orders2);

        String uri = "/orders";

        Mockito.when(orderService.getOrderList())
                .thenReturn(mockOrdersList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();


        String expectedJson = this.mapToJson(mockOrdersList);
        String outputJson = result.getResponse().getContentAsString();

        assertEquals(expectedJson, outputJson);
    }

    @Test
    void testGetOrderById_Existing() throws Exception {
        OrderItem orderItem1 = new OrderItem(
                100,
                2
        );
        OrderItem orderItem2 = new OrderItem(
                201,
                5
        );
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        Orders mockOrder = new Orders(10);
        mockOrder.setOrderItems(orderItemList);

        String uri = "/orders/10";

        Mockito.when(orderService.getOrderById(Mockito.any(Integer.class)))
                .thenReturn(mockOrder);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJson(mockOrder);
        String outputJson = result.getResponse().getContentAsString();

        assertEquals(expectedJson, outputJson);

    }

    @Test
    void testDelete_whenExisting() throws Exception {
        OrderItem orderItem1 = new OrderItem(
                100,
                2
        );
        OrderItem orderItem2 = new OrderItem(
                201,
                5
        );
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        Orders mockOrder = new Orders(10);
        mockOrder.setOrderItems(orderItemList);

        String uri = "/orders/10";

        Mockito.when(orderService.getOrderById(Mockito.any(Integer.class)))
                .thenReturn(mockOrder);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJson(mockOrder);
        String outputJson = result.getResponse().getContentAsString();

        assertEquals(expectedJson, outputJson);
    }

    @Test
    void testDelete_whenNonExisting() {

    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}