package com.example.order.management.system.service;

import com.example.order.management.system.exception.OrderAlreadyExistsException;
import com.example.order.management.system.exception.OrderNotFoundException;
import com.example.order.management.system.exception.ProductNotAvailableException;
import com.example.order.management.system.exception.ProductNotFoundException;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.modal.Product;
import com.example.order.management.system.repository.OrderItemRepository;
import com.example.order.management.system.repository.OrderRepository;
import com.example.order.management.system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
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

//    public Boolean checkIfAllItemsAreAvailable(Orders orders)
//    {
//        for(OrderItem item: orders.getOrderItems())
//        {
//            Optional<Product> product = Optional.ofNullable(productService.getProductById(item.getProductId()));
//            if(product.isPresent())
//            {
//                Product newProduct = product.get();
//                if(newProduct.getQuantity()<item.getQuantity())
//                    return false;
//            }
//            else
//            {
//                throw new ProductNotFoundException("Product ordered is not found");
//            }
//        }
//        return true;
//    }
//    public void updateProductCountForOrder(Orders orders)
//    {
//        for(OrderItem item: orders.getOrderItems())
//        {
//            Optional<Product> product = Optional.ofNullable(productService.getProductById(item.getProductId()));
//            if(product.isPresent())
//            {
//                Product newProduct = product.get();
//                newProduct.setQuantity(newProduct.getQuantity()-item.getQuantity());
//                productService.updateProduct(item.getProductId(), product.get());
//            }
//            else
//            {
//                throw new ProductNotFoundException("Product ordered is not found");
//            }
//        }
//    }
    @Override
    public String placeOrder(Orders orders) {
        orders.setOrderDate(LocalDateTime.now());
        Optional<Orders> existingOrder
                = orderRepository.findById(orders.getId());
        if (existingOrder.isEmpty()) {
            productService.checkAndUpdateProductForOrderItem(orders.getOrderItems());
            orders.calculateTotalPrice();
            orderRepository.save(orders);
            for(OrderItem item: orders.getOrderItems())
            {

                orderItemService.addOrderItem(item);
            }
            return "Order Placed successfully";
        }
        else
            throw new OrderAlreadyExistsException(
                    "Order already exists!!");
    }

    @Override
    public void delete(Orders orders){
        this.orderRepository.delete(orders);
    }
}
