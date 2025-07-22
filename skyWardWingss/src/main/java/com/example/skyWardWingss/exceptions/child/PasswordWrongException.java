package com.example.skyWardWingss.exceptions.child;

import com.example.skyWardWingss.exceptions.BadRequestException;

public class PasswordWrongException extends BadRequestException {
    public PasswordWrongException() {
        super("Old password entered incorrectly or new passwords do not match");
    }
}
