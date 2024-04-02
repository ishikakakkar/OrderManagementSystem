package com.example.order.management.system.exception;

public class OrderItemAlreadyExistsException extends RuntimeException{
    private String message;

    public OrderItemAlreadyExistsException() {}

    public OrderItemAlreadyExistsException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
