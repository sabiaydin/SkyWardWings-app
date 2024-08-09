package com.example.skyWardWingss.service.impl;

import com.example.skyWardWingss.model.dto.request.EmailRequest;
import com.example.skyWardWingss.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendSimpleEmail(EmailRequest emailRequest) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailRequest.getReceiver());
        String myEmail = "sabinamammadova871@gmail.com";
        msg.setFrom(myEmail);
        msg.setSubject(emailRequest.getSubject());
        msg.setText(emailRequest.getText());
        javaMailSender.send(msg);
        log.info("Successfully sent email to {}", emailRequest.getReceiver());

    }
}
