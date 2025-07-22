package com.example.skyWardWingss.exceptions.child;

import com.example.skyWardWingss.exceptions.NotFoundException;

public class AirplaneNotFoundException extends NotFoundException {
    public AirplaneNotFoundException(String message) {
        super(message);
    }
}
