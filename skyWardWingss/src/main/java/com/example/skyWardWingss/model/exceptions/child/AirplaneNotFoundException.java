package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.NotFoundException;

public class AirplaneNotFoundException extends NotFoundException {
    public AirplaneNotFoundException(String message) {
        super(message);
    }
}
