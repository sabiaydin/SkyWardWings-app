package com.example.skyWardWingss.exceptions;

public class SeatAlreadyReservedException extends RuntimeException{
    public SeatAlreadyReservedException(String message){
        super(message);
    }
}
