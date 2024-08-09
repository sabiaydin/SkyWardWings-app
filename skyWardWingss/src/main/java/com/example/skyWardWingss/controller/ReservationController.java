package com.example.skyWardWingss.controller;

import com.example.skyWardWingss.model.dto.request.ReservationRequestDto;
import com.example.skyWardWingss.model.dto.response.ReservationResponse;
import com.example.skyWardWingss.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/reserve-flight")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponse> reserveFlight(@RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationResponse reservationResponse = reservationService.reserveFlight(reservationRequestDto);
        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        ReservationResponse reservationResponse = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ReservationResponse>> getAllReservations(Pageable pageable) {
        Page<ReservationResponse> reservationResponses = reservationService.getAll(pageable);
        return ResponseEntity.ok(reservationResponses);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
