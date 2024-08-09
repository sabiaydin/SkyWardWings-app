package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.NotFoundException;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
