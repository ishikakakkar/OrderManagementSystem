package com.example.order.management.system.service;

import com.example.order.management.system.modal.Orders;

import java.util.ArrayList;

public interface OrderService {
    ArrayList<Orders> getOrderList();
    Orders getOrderById(int id);
    String placeOrder(Orders orders);

    void delete(Orders orders);
//    Order updateOrder(Order order);
}
