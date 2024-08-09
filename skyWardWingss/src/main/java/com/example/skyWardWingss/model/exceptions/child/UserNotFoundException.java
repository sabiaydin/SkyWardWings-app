package com.example.skyWardWingss.model.exceptions.child;

import com.example.skyWardWingss.model.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(){
        super("User");
    }
}
