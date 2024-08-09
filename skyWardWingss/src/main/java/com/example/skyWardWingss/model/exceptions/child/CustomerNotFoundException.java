package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.NotFoundException;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
