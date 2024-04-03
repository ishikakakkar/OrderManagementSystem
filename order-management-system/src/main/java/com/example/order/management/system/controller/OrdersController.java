package com.example.order.management.system.controller;

import com.example.order.management.system.exception.*;
import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.service.OrderItemService;
import com.example.order.management.system.service.OrderService;
import com.example.order.management.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class OrdersController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Orders orders)
    {
        try {
            return orderService.placeOrder(orders);
        }
        catch (OrderItemNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (OrderAlreadyExistsException | OrderDetailsInvalidException | ProductQuantityNotEnoughException | ProductDetailsInvalidException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/orders")
    public ArrayList<Orders> getOrderList()
    {
        try {
            return orderService.getOrderList();
        }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("orders/{id}")
    public Orders getOrderById(@PathVariable int id)
    {
        try {
            return orderService.getOrderById(id);
        }
        catch (OrderNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("orders/{id}")
    public String deleteOrder(@PathVariable("id") int id){
        try {
            return orderService.deleteOrder(id);
        }
        catch (OrderNotFoundException | ProductNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (ProductDetailsInvalidException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());

        }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
