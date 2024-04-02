package com.example.order.management.system.service;

import com.example.order.management.system.exception.ProductNotFoundException;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {
    ArrayList<Product> getProductList();
    Product getProductById(int id);
    String addProduct(Product product);
    Product updateProduct(int id, Product product) throws ProductNotFoundException;
    void checkAndUpdateProductForOrderItem(List<OrderItem> orderItems);
}
