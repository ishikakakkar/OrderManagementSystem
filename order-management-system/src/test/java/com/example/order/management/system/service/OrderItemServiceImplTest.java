package com.example.order.management.system.service;

import com.example.order.management.system.exception.OrderItemAlreadyExistsException;
import com.example.order.management.system.exception.ProductAlreadyExistsException;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Product;
import com.example.order.management.system.repository.OrderItemRepository;
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

class OrderItemServiceImplTest {

    @InjectMocks
    private OrderItemServiceImpl orderItemServiceImpl;

    @Mock
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderItemList() {
        OrderItem orderItem1 = new OrderItem(
                100,
                2
        );
        OrderItem orderItem2 = new OrderItem(
                201,
                5
        );
        ArrayList<OrderItem> orderItemList= new ArrayList<>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        Mockito.when(orderItemRepository.findAll())
                .thenReturn(orderItemList);

        ArrayList<OrderItem> returnList = orderItemServiceImpl.getOrderItemList();

        assertEquals(returnList, orderItemList);
    }

    @Test
    void testGetOrderItemById_Existing() {
        OrderItem orderItem = new OrderItem(
                100,
                2
        );

        Mockito.when(orderItemRepository.findById(10))
                .thenReturn(Optional.of(orderItem));

        OrderItem returnOrderItem = orderItemServiceImpl.getOrderItemById(10);

        assertEquals(returnOrderItem, orderItem);
    }

    @Test
    void testGetOrderItemById_NonExisting() {
        Mockito.when(orderItemRepository.findById(10))
                .thenReturn(Optional.empty());

        OrderItem returnOrderItem = orderItemServiceImpl.getOrderItemById(10);

        assertNull(returnOrderItem);
    }


    @Test
    void testAddOrderItem_NewOrderItem() {
        OrderItem orderItem = new OrderItem(
                100,
                2
        );

        Mockito.when(orderItemRepository.findById(10))
                .thenReturn(null);

        Mockito.when(orderItemRepository.save(orderItem))
                .thenReturn(orderItem);

        String msg = orderItemServiceImpl.addOrderItem(orderItem);

        assertEquals(msg, "Order Item added successfully");

    }

//    @Test
//    void testAddOrderItem_ExistingOrderItem() {
//        OrderItem orderItem = new OrderItem(
//                100,
//                2
//        );
//
//        orderItem.setId(10);
//
//        Mockito.when(orderItemRepository.findById(10))
//                .thenReturn(Optional.of(orderItem));
//
//        assertThrows(OrderItemAlreadyExistsException.class, () -> orderItemServiceImpl.addOrderItem(orderItem));
//
//    }
}