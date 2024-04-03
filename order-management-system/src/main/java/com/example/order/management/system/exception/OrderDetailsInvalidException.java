package com.example.order.management.system.exception;

public class OrderDetailsInvalidException extends RuntimeException {
    private String message;

    public OrderDetailsInvalidException() {}

    public OrderDetailsInvalidException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
