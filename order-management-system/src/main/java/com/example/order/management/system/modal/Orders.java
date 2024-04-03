package com.example.order.management.system.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime orderDate;
    private int totalPrice;
    @OneToMany(targetEntity=OrderItem.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public Orders() {
    }

    public Orders(int userId) {
        this.userId = userId;
        this.orderDate = LocalDateTime.now();
        this.totalPrice = 0;
        orderItems = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void calculateTotalPrice()
    {
        for(OrderItem item: this.orderItems)
        {
//            item.setOrderId(this.getId());
            item.calculateTotalPrice();
            this.totalPrice += item.getTotalPrice();
        }
    }
    public boolean isValid()
    {
        if(userId<=0)
            return false;
        for(OrderItem item:orderItems)
        {
            if(!item.isValid())
                return false;
        }
        return true;
    }
}
