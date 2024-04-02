package com.example.order.management.system.repository;

import com.example.order.management.system.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    ArrayList<ProductInfo> getProductList();
    Product getProductByName(String name);
}
