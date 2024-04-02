package com.example.order.management.system.controller;

import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.modal.Product;
import com.example.order.management.system.service.OrderItemService;
import com.example.order.management.system.service.OrderService;
import com.example.order.management.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ProductController {

//    ProductInfoServiceImpl productInfoServiceImpl;
    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public String addProduct(@RequestBody Product product)
    {
        //Onboard a product
//        ProductInfo productInfo = new ProductInfo("Iphone", "Phone", 100, 90000);

        return productService.addProduct(product);
    }

    @GetMapping("/products")
    public ArrayList<Product> getProductList()
    {
        return productService.getProductList();
    }

    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable int id)
    {
        return productService.getProductById(id);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        product.setId(id);
        return productService.updateProduct(id, product);
    }



}
