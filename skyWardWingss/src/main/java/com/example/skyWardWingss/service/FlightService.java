package com.example.skyWardWingss.service;


import com.example.skyWardWingss.model.dto.request.FlightRequestDto;
import com.example.skyWardWingss.model.dto.request.FlightUpdateDto;
import com.example.skyWardWingss.model.dto.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    List<FlightResponse> searchFlight(String departure, String destination, LocalDate departureTime);

    FlightResponse getById(Long id);

    Page<FlightResponse> getAll(Pageable pageable);

    void update(Long id, FlightUpdateDto flightUpdateDto);

    void delete(Long id);

    FlightResponse add(FlightRequestDto flightRequestDto);
}
