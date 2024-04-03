package com.example.order.management.system.service;

import com.example.order.management.system.exception.OrderAlreadyExistsException;
import com.example.order.management.system.exception.OrderDetailsInvalidException;
import com.example.order.management.system.exception.OrderItemAlreadyExistsException;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService{
    @Autowired
    OrderItemRepository orderItemRepository;
    @Override
    public ArrayList<OrderItem> getOrderItemList() {
        return (ArrayList<OrderItem>)orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        return orderItem.orElse(null);
    }

    @Override
    @Transactional
    public String addOrderItem(OrderItem orderItem) {
//        orderItem.setOrderDate(LocalDateTime.now());

//        Optional<OrderItem> existingOrderItem
//                = orderItemRepository.findById(orderItem.getId());
//        if (existingOrderItem.isEmpty()) {
        if(orderItem.isValid()) {
            orderItemRepository.save(orderItem);
            return "Order Item added successfully";
        }
        else
            throw new OrderDetailsInvalidException("Order Item quantity is not valid");
//        }
//        else
//            throw new OrderItemAlreadyExistsException(
//                    "Order Item already exists!!");
    }

    @Override
    @Transactional
    public void deleteOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.deleteAll(orderItems);
    }
}
