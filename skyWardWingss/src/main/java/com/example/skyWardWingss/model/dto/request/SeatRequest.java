package com.example.skyWardWingss.model.dto.request;

import lombok.Data;

@Data
public class SeatRequest {
    private Long id;
    private String seatNumber;
    private String seatType;
    private Integer price;
}
