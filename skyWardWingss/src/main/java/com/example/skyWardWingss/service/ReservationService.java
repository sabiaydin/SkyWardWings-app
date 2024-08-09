package com.example.skyWardWingss.service;

import com.example.skyWardWingss.model.dto.request.ReservationRequestDto;
import com.example.skyWardWingss.model.dto.response.ReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
    ReservationResponse reserveFlight(ReservationRequestDto reservationRequestDto);

    void deleteById(Long id);

    ReservationResponse getReservationById(Long id);

    Page<ReservationResponse> getAll(Pageable pageable);
}
