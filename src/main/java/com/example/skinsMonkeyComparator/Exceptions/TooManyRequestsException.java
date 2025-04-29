package com.example.skinsMonkeyComparator.Exceptions;

public class TooManyRequestsException extends Exception{

    public TooManyRequestsException() {
    }

    public TooManyRequestsException(String message) {
        super(message);
    }
}