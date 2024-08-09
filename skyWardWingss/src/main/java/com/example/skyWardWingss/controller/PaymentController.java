package com.example.skyWardWingss.controller;


import com.example.skyWardWingss.model.dto.request.PaymentRequestDto;
import com.example.skyWardWingss.model.dto.response.PaymentResponse;
import com.example.skyWardWingss.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponse paymentResponse = paymentService.pay(paymentRequestDto);
        return ResponseEntity.ok(paymentResponse);
}}
