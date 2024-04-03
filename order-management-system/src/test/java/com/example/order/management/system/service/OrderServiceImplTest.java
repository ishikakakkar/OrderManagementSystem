package com.example.order.management.system.service;

import com.example.order.management.system.exception.OrderDetailsInvalidException;
import com.example.order.management.system.exception.OrderItemNotFoundException;
import com.example.order.management.system.exception.OrderNotFoundException;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.modal.Product;
import com.example.order.management.system.repository.OrderItemRepository;
import com.example.order.management.system.repository.OrderRepository;
import com.example.order.management.system.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private OrderItemServiceImpl orderItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderList() {
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

        ArrayList<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders1);
        ordersList.add(orders2);

        Mockito.when(orderRepository.findAll())
                .thenReturn(ordersList);

        ArrayList<Orders> returnList = orderService.getOrderList();

        assertEquals(returnList, ordersList);
    }

    @Test
    void testGetOrderById_Existing() {
        OrderItem orderItem1 = new OrderItem(
                100,
                2
        );
        OrderItem orderItem2 = new OrderItem(
                201,
                5
        );
        ArrayList<OrderItem> orderItemList1 = new ArrayList<>();
        orderItemList1.add(orderItem1);
        orderItemList1.add(orderItem2);

        Orders orders1 = new Orders(10);
        orders1.setOrderItems(orderItemList1);

        Mockito.when(orderRepository.findById(10))
                .thenReturn(Optional.of(orders1));

        Orders returnOrders = orderService.getOrderById(10);

        assertEquals(returnOrders, orders1);
    }

    @Test
    void testGetOrderById_NonExisting() {
        Mockito.when(orderRepository.findById(10))
                .thenReturn(Optional.empty());

//        Orders returnOrders = orderServiceImpl.getOrderById(10);

        assertThrows(OrderNotFoundException.class, ()-> orderService.getOrderById(10));
    }

    @Test
    void testPlaceOrder() {
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

        Orders orders = new Orders(10);
        orders.setOrderItems(orderItemList);
        orders.setId(10);

        Product product1 = new Product(
                "JBL Speaker",
                "Speakers",
                100,
                10000
        );
        Product product2 = new Product(
                "Titan Watch",
                "Wrist Watch",
                20,
                2000
        );

        Mockito.when(orderRepository.findById(10))
                .thenReturn(Optional.empty());

        Mockito.doNothing().when(productService).updateProductQuantityForPlacingOrder(orderItemList);

        Mockito.when(orderRepository.save(orders))
                .thenReturn(orders);

        Mockito.when(orderItemService.addOrderItem(Mockito.any(OrderItem.class)))
                .thenReturn("Order Item added successfully");

        Mockito.when(productRepository.findById(100))
                .thenReturn(Optional.of(product1));

        Mockito.when(productRepository.findById(201))
                .thenReturn(Optional.of(product2));

        Mockito.when(productService.updateProduct(100, product1))
                .thenReturn(product1);

        Mockito.when(productService.updateProduct(201, product2))
                .thenReturn(product2);

        String msg = orderService.placeOrder(orders);

        assertEquals(msg, "Order Placed successfully");
    }

    @Test
    void testPlaceOrder_EmptyList()
    {
        Orders orders = new Orders(10);

        Mockito.when(orderRepository.findById(10))
                .thenReturn(Optional.empty());

        assertThrows(OrderItemNotFoundException.class, ()-> orderService.placeOrder(orders));
    }

    @Test
    void testPlaceOrder_InvalidOrder() {
        OrderItem orderItem1 = new OrderItem(
                100,
                2
        );
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);

        Orders orders = new Orders(-10);
        orders.setOrderItems(orderItemList);
        orders.setId(10);

        assertThrows(OrderDetailsInvalidException.class, ()->orderService.placeOrder(orders));
    }

    @Test
    void testPlaceOrder_EmptyOrderList() {
        Orders orders = new Orders(10);
        orders.setId(10);

        Mockito.when(orderRepository.findById(10))
                .thenReturn(Optional.empty());

        assertThrows(OrderItemNotFoundException.class, ()->orderService.placeOrder(orders));
    }

    @Test
    void testDeleteOrder()
    {
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

        Orders orders = new Orders(10);
        orders.setOrderItems(orderItemList);
        orders.setId(10);
        Mockito.when(orderRepository.findById(10))
                .thenReturn(Optional.of(orders));

        Mockito.doNothing().when(productService).updateProductQuantityForOrderDeletion(orderItemList);

        Mockito.doNothing().when(orderItemService).deleteOrderItems(orderItemList);

        Mockito.doNothing().when(orderRepository).delete(orders);

        String msg = orderService.deleteOrder(10);

        assertEquals("Order deleted successfully", msg);

    }

    @Test
    void testDeleteOrder_NonExisting()
    {
        Orders orders = new Orders(10);

        Mockito.when(orderRepository.findById(10))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, ()-> orderService.deleteOrder(10));
    }
}