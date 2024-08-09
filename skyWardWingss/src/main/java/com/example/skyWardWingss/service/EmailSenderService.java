package com.example.skyWardWingss.service;

import com.example.skyWardWingss.model.dto.request.EmailRequest;


public interface EmailSenderService {
    void sendSimpleEmail(EmailRequest emailRequest);

}
