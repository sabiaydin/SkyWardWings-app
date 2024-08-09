package com.example.skyWardWingss.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public  class AirplaneResponse{
    private String  name;
    private Integer capacity;
    private List<SeatResponse> seat;
}
