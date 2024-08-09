package com.example.skyWardWingss.model.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private Long ReservationId;
    private Double amount;
    private CardDetailedDto cardDetailedDto;
    private Long companyId;
}

