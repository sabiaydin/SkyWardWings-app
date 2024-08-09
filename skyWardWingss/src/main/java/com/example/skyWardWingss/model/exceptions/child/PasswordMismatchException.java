package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException(){
        super("Retry password do not matching new password");
    }
}
