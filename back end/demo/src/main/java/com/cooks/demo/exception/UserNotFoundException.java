package com.cooks.demo.exception;


public class UserNotFoundException extends Exception {
    /**
     * custom exception
     * @param message
     */
    public UserNotFoundException(String message){
        super(message);
    }
}
