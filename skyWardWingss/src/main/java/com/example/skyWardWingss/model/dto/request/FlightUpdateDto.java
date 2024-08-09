package com.example.skyWardWingss.model.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlightUpdateDto {
    private String departure;
    private LocalDateTime departureTime;
    private String destination;
    private LocalDateTime arrivalTime;
    private List<TypePriceUpdateDto> typePrice;

}
