package com.example.skyWardWingss.exceptions;

public class InvalidFlightIdException extends RuntimeException{
    public InvalidFlightIdException(String message){
        super(message);
    }
}
