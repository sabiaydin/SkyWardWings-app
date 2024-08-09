package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.NotFoundException;

public class FlightNotFoundException extends NotFoundException {
    public FlightNotFoundException(String message) {
        super(message);
    }
}
