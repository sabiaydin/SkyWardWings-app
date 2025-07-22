package com.example.skyWardWingss.exceptions.child;

import com.example.skyWardWingss.exceptions.NotFoundException;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
