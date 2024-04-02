package com.example.order.management.system.exception;

public class ProductNotAvailableException extends RuntimeException{
    private String message;

    public ProductNotAvailableException() {}

    public ProductNotAvailableException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
