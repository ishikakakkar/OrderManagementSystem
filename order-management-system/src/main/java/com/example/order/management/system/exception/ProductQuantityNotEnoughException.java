package com.example.order.management.system.exception;

public class ProductQuantityNotEnoughException extends RuntimeException {
    private String message;

    public ProductQuantityNotEnoughException() {}

    public ProductQuantityNotEnoughException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
