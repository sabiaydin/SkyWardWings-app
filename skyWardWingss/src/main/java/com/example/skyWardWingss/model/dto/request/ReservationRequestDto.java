package com.example.skyWardWingss.model.dto.request;

import lombok.Data;

@Data
public class ReservationRequestDto {
    private Long flightId;
    private String seatNumber;
    private String seatType;
  private CustomerRequestDto customerRequestDto;
}
