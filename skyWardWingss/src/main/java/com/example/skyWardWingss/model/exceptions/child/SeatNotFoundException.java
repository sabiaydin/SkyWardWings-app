package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.NotFoundException;

public class SeatNotFoundException extends NotFoundException {
    public SeatNotFoundException(String message) {
        super(message);
    }
}
