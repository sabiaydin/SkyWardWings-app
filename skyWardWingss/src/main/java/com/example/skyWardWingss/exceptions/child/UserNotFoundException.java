package com.example.skyWardWingss.exceptions.child;

import com.example.skyWardWingss.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(){
        super("User");
    }
}
