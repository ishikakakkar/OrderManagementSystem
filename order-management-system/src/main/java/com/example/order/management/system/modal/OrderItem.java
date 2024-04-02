package com.example.order.management.system.modal;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productId;
//    @OneToOne(targetEntity=Product.class,cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, orphanRemoval = true)
//    private Product product;
    private int quantity;
    private int unitPrice;
    private int totalPrice;

    public OrderItem() {
    }

    public OrderItem(int productId, int quantity) {
//        this.orderId = orderId;
        this.productId = productId;
//        this.product = product;
        this.quantity = quantity;
//        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

//    public Product getProduct() {
//        return product;
//    }

//    public void setProduct(Product product) {
//        this.product = product;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice()
    {
        this.totalPrice = this.unitPrice * this.quantity;
    }
}
