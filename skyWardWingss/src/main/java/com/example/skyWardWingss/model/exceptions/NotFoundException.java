package com.example.skyWardWingss.model.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message + " Not found!");
    }
}
