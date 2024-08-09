package com.example.skyWardWingss.controller;

import com.example.skyWardWingss.model.dto.request.FlightRequestDto;
import com.example.skyWardWingss.model.dto.request.FlightUpdateDto;
import com.example.skyWardWingss.model.dto.response.FlightResponse;
import com.example.skyWardWingss.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(
            @RequestParam String departure,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureTime) {

        List<FlightResponse> flights = flightService.searchFlight(departure, destination, departureTime);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FlightResponse> getById(@PathVariable Long id) {
        FlightResponse flightResponse = flightService.getById(id);
        return ResponseEntity.ok(flightResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<FlightResponse>> getAll(Pageable pageable) {
        Page<FlightResponse> flightResponses = flightService.getAll(pageable);
        return ResponseEntity.ok(flightResponses);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FlightResponse> add(@RequestBody FlightRequestDto flightRequestDto) {
        FlightResponse newFlight = flightService.add(flightRequestDto);
        return ResponseEntity.ok(newFlight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFlight(@PathVariable Long id, @RequestBody FlightUpdateDto flightUpdateDto) {
        flightService.update(id, flightUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
