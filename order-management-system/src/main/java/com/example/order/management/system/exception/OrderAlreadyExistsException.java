package com.example.order.management.system.exception;

public class OrderAlreadyExistsException extends RuntimeException{
    private String message;

    public OrderAlreadyExistsException() {}

    public OrderAlreadyExistsException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
