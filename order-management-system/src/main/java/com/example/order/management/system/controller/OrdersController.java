package com.example.order.management.system.controller;

import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.service.OrderItemService;
import com.example.order.management.system.service.OrderService;
import com.example.order.management.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class OrdersController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Orders orders)
    {
        return orderService.placeOrder(orders);
    }

    @GetMapping("/orders")
    public ArrayList<Orders> getOrderList()
    {
        return orderService.getOrderList();
    }

    @GetMapping("orders/{id}")
    public Orders getOrderById(@PathVariable int id)
    {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("orders/{id}")
    public String deleteOrder(@PathVariable("id") int id){
        return orderService.deleteOrder(id);
    }
}
