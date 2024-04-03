package com.example.order.management.system.controller;

import com.example.order.management.system.exception.ProductAlreadyExistsException;
import com.example.order.management.system.exception.ProductDetailsInvalidException;
import com.example.order.management.system.exception.ProductNotFoundException;
import com.example.order.management.system.modal.Orders;
import com.example.order.management.system.modal.Product;
import com.example.order.management.system.service.OrderItemService;
import com.example.order.management.system.service.OrderService;
import com.example.order.management.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public String addProduct(@RequestBody Product product) {
        try {
            return productService.addProduct(product);
        }
        catch (ProductDetailsInvalidException | ProductAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/products")
    public ArrayList<Product> getProductList()
    {
        try {
            return productService.getProductList();
        }
        catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable int id)
    {
        try {
            return productService.getProductById(id);
        }
        catch (ProductNotFoundException e)
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        try {
            return productService.updateProduct(id, product);
        }
        catch (ProductDetailsInvalidException e)
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch (ProductNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
