package com.example.skyWardWingss.model.exceptions;

public class SeatAlreadyReservedException extends RuntimeException{
    public SeatAlreadyReservedException(String message){
        super(message);
    }
}
