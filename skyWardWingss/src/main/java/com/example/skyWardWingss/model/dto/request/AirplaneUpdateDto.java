package com.example.skyWardWingss.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AirplaneUpdateDto {
    private String name;
    private Integer capacity;
    private List<SeatRequest> seat;
}
