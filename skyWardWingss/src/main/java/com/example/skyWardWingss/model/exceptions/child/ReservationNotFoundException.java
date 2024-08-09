package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
