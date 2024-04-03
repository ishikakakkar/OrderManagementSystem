package com.example.order.management.system.service;

import com.example.order.management.system.exception.ProductAlreadyExistsException;
import com.example.order.management.system.exception.ProductDetailsInvalidException;
import com.example.order.management.system.exception.ProductNotFoundException;
import com.example.order.management.system.exception.ProductQuantityNotEnoughException;
import com.example.order.management.system.modal.OrderItem;
import com.example.order.management.system.modal.Product;
import com.example.order.management.system.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ArrayList<Product> getProductList() {
        return (ArrayList<Product>) productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent())
            return product.get();
        else
            throw new ProductNotFoundException("Product Not found");
    }

    @Override
    @Transactional
    public String addProduct(Product product) {
        Product existingProduct
                = productRepository.getProductByName(product.getName());
        if (existingProduct == null) {
            if(product.isValid())
            {
                productRepository.save(product);
                return "Product Info added successfully";
            }
            else
                throw new ProductDetailsInvalidException("Product details are invalid");
        }
        else
            throw new ProductAlreadyExistsException(
                    "Product already exists!!");

    }

    @Override
    @Transactional
    public Product updateProduct(int id, Product product) throws ProductNotFoundException {
        product.setId(id);
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            if(product.isValid()) {
                if(!product.equals(existingProduct.get())) {
                    return productRepository.save(product);
                }
                else
                    throw new ProductDetailsInvalidException("Product details provided are same as already present");
            }
            else
                throw new ProductDetailsInvalidException("Product details invalid");
        }
        else
        {
            throw new ProductNotFoundException("Product Not Found");
        }
    }

    @Override
    @Transactional
    public void updateProductQuantityForPlacingOrder(List<OrderItem> orderItems) {
        for(OrderItem item: orderItems)
        {
            Optional<Product> product = productRepository.findById(item.getProductId());
            if(product.isPresent())
            {
                Product newProduct = product.get();
                item.setUnitPrice(product.get().getPrice());
                if(newProduct.getQuantity()<item.getQuantity())
                    throw new ProductQuantityNotEnoughException("Product Quantity not enough for product id:" + item.getProductId());
                newProduct.setQuantity(newProduct.getQuantity()-item.getQuantity());
                this.updateProduct(item.getProductId(), product.get());
            }
            else
            {
                throw new ProductNotFoundException("Product ordered is not found");
            }
        }

    }

    @Override
    @Transactional
    public void updateProductQuantityForOrderDeletion(List<OrderItem> orderItems) {
        //Update product quantities
        for(OrderItem item: orderItems)
        {
            Optional<Product> product = Optional.ofNullable(this.getProductById(item.getProductId()));
            if(product.isPresent())
            {
                Product newProduct = product.get();
                newProduct.setQuantity(newProduct.getQuantity()+item.getQuantity());
                this.updateProduct(item.getProductId(), product.get());
            }
//            else
//            {
//                throw new ProductNotFoundException("Product is not found");
//            }
        }
    }


}
