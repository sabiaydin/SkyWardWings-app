package com.example.skyWardWingss.model.exceptions;

public class InvalidFlightIdException extends RuntimeException{
    public InvalidFlightIdException(String message){
        super(message);
    }
}
