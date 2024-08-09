package com.example.skyWardWingss.model.dto.response;

import lombok.Data;

@Data
public class ReservationResponse {
    private Long reservationId;
    private String seatNumber;
    private Integer price;
    private String reservationStatus;
    private FlightResponse flight;
    private CustomerResponse customer;

}
