package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.BadRequestException;

public class PasswordWrongException extends BadRequestException {
    public PasswordWrongException() {
        super("Old password entered incorrectly or new passwords do not match");
    }
}
