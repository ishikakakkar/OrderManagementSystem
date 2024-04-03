package com.example.order.management.system.service;

import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Orders;

import java.util.ArrayList;
import java.util.List;

public interface OrderItemService {
    ArrayList<OrderItem> getOrderItemList();
    OrderItem getOrderItemById(int id);
    String addOrderItem(OrderItem orderItem);
//    OrderItem updateOrderItem(OrderItem orderItem);
    void deleteOrderItems(List<OrderItem> orderItems);
}
