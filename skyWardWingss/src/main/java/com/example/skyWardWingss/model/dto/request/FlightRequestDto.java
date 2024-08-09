package com.example.skyWardWingss.model.dto.request;

import jakarta.validation.constraints.AssertFalse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlightRequestDto {
    private String departure;
    private LocalDateTime departureTime;
    private String destination;
    private LocalDateTime arrivalTime;
    private Long airplaneId;
    private List<TypePriceDto> typePrice;
}
