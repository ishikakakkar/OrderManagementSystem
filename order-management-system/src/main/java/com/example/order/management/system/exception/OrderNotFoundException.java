package com.example.order.management.system.exception;

public class OrderNotFoundException extends RuntimeException{
    private String message;

    public OrderNotFoundException() {}

    public OrderNotFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
