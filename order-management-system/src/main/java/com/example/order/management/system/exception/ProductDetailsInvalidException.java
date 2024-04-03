package com.example.order.management.system.exception;

public class ProductDetailsInvalidException extends RuntimeException{
    private String message;

    public ProductDetailsInvalidException() {}

    public ProductDetailsInvalidException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
