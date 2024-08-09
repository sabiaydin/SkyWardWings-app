package com.example.skyWardWingss.service;

import com.example.skyWardWingss.model.dto.request.AirplaneRequestDto;
import com.example.skyWardWingss.model.dto.request.AirplaneUpdateDto;
import com.example.skyWardWingss.model.dto.response.AirplaneResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirplaneService {
    AirplaneResponse getById(Long id);

    Page<AirplaneResponse> getAll(Pageable pageable);

    void update(Long id, AirplaneUpdateDto airplaneUpdateDto);

    void delete(Long id);

    AirplaneResponse add(AirplaneRequestDto airplaneRequestDto);
}
