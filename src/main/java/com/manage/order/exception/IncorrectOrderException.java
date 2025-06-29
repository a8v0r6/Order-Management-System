package com.manage.order.exception;

public class IncorrectOrderException extends RuntimeException {
    public IncorrectOrderException(String message){
        super(message);
    }
}
