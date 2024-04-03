package com.example.order.management.system.service;

import com.example.order.management.system.exception.*;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public ArrayList<Orders> getOrderList() {
        return (ArrayList<Orders>)orderRepository.findAll();
    }

    @Override
    public Orders getOrderById(int id) {
        Optional<Orders> order = orderRepository.findById(id);
        if(order.isPresent())
            return order.orElse(null);
        else
            throw new OrderNotFoundException("Order not found");
    }

    @Override
    @Transactional
    public String placeOrder(Orders orders) {
        orders.setOrderDate(LocalDateTime.now());
        if(orders.isValid())
        {
            Optional<Orders> existingOrder
                    = orderRepository.findById(orders.getId());
            if (existingOrder.isEmpty()) {
                if(!orders.getOrderItems().isEmpty())
                {
                    productService.updateProductQuantityForPlacingOrder(orders.getOrderItems());

                    orders.calculateTotalPrice();
                    orderRepository.save(orders);
                    for(OrderItem item: orders.getOrderItems())
                    {

                        orderItemService.addOrderItem(item);
                    }
                    return "Order Placed successfully";
                }
                else
                    throw new OrderItemNotFoundException("Order Item list is empty");
            }
            else
                throw new OrderAlreadyExistsException(
                        "Order already exists!!");
        }
        else
            throw new OrderDetailsInvalidException("Order details are invalid");

    }

    @Override
    @Transactional
    public String deleteOrder(int id){
        Optional<Orders> existingOrder = Optional.ofNullable(this.getOrderById(id));
        if(existingOrder.isPresent()){
            productService.updateProductQuantityForOrderDeletion(existingOrder.get().getOrderItems());
            orderItemService.deleteOrderItems(existingOrder.get().getOrderItems());
            orderRepository.delete(existingOrder.get());
            return "Order deleted successfully";
        }
        throw new OrderNotFoundException("Order not found");

    }
}
