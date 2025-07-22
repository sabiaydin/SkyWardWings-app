package com.example.skyWardWingss.exceptions.child;

import com.example.skyWardWingss.exceptions.NotFoundException;

public class FlightNotFoundException extends NotFoundException {
    public FlightNotFoundException(String message) {
        super(message);
    }
}
