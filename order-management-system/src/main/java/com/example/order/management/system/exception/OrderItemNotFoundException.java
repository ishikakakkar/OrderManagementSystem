package com.example.order.management.system.exception;

public class OrderItemNotFoundException extends RuntimeException{
    private String message;

    public OrderItemNotFoundException() {}

    public OrderItemNotFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
