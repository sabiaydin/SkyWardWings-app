package com.example.skyWardWingss.controller;

import com.example.skyWardWingss.model.dto.request.AirplaneRequestDto;
import com.example.skyWardWingss.model.dto.request.AirplaneUpdateDto;
import com.example.skyWardWingss.model.dto.response.AirplaneResponse;
import com.example.skyWardWingss.service.AirplaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/airplanes")
@RequiredArgsConstructor
public class AirplaneController {
    private final AirplaneService airplaneService;

    @GetMapping("/{id}")
    public ResponseEntity<AirplaneResponse> getById(@PathVariable Long id) {
        AirplaneResponse airplaneResponse = airplaneService.getById(id);
        return ResponseEntity.ok(airplaneResponse);
    }

    @GetMapping
    public ResponseEntity<Page<AirplaneResponse>> getAll(Pageable pageable) {
        Page<AirplaneResponse> airplaneResponses = airplaneService.getAll(pageable);
        return ResponseEntity.ok(airplaneResponses);
    }

    @PostMapping
    public ResponseEntity<AirplaneResponse> add(@RequestBody AirplaneRequestDto airplaneRequestDto) {
        AirplaneResponse newAirplane = airplaneService.add(airplaneRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAirplane);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AirplaneUpdateDto airplaneUpdateDto) {
        airplaneService.update(id, airplaneUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        airplaneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
