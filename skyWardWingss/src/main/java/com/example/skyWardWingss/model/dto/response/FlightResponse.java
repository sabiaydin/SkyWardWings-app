package com.example.skyWardWingss.model.dto.response;

import com.example.skyWardWingss.model.dto.request.TypePriceDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlightResponse {
    private Long id;
    private String departure;
    private LocalDateTime departureTime;
    private String destination;
    private LocalDateTime arrivalTime;
    private String duration;
    private List<TypePriceDto> typePrice;
    private AirplaneFlightResponse airplane;

}
