package com.example.skyWardWingss.exceptions.child;

import com.example.skyWardWingss.exceptions.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
