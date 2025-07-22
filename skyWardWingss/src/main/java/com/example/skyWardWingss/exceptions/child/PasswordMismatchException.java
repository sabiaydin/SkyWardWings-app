package com.example.skyWardWingss.exceptions.child;

import com.example.skyWardWingss.exceptions.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException(){
        super("Retry password do not matching new password");
    }
}
