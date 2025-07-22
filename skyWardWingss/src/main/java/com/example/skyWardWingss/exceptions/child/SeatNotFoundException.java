package com.example.skyWardWingss.exceptions.child;

import com.example.skyWardWingss.exceptions.NotFoundException;

public class SeatNotFoundException extends NotFoundException {
    public SeatNotFoundException(String message) {
        super(message);
    }
}
