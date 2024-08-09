package com.example.skyWardWingss.model.dto.response;
import com.example.skyWardWingss.model.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Double amount;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;
    private ReservationResponse reservation;
    private CompanyResponse company;
}
