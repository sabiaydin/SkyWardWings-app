package com.example.skyWardWingss.service;

import com.example.skyWardWingss.model.dto.request.PaymentRequestDto;
import com.example.skyWardWingss.model.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse pay(PaymentRequestDto paymentRequestDto);
}
