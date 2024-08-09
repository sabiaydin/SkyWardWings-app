package com.example.skyWardWingss.service.impl;

import com.example.skyWardWingss.dao.entity.Airplane;
import com.example.skyWardWingss.dao.entity.Seat;
import com.example.skyWardWingss.model.exceptions.child.AirplaneNotFoundException;
import com.example.skyWardWingss.model.exceptions.child.SeatNotFoundException;
import com.example.skyWardWingss.mapper.AirplaneMapper;
import com.example.skyWardWingss.mapper.SeatMapper;
import com.example.skyWardWingss.model.dto.request.AirplaneRequestDto;
import com.example.skyWardWingss.model.dto.request.AirplaneUpdateDto;
import com.example.skyWardWingss.model.dto.request.SeatRequest;
import com.example.skyWardWingss.model.dto.response.AirplaneResponse;
import com.example.skyWardWingss.dao.repository.AirplaneRepository;
import com.example.skyWardWingss.dao.repository.SeatRepository;
import com.example.skyWardWingss.service.AirplaneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirplaneServiceImpl implements AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final AirplaneMapper airplaneMapper;
    private final SeatMapper seatMapper;
    private final SeatRepository seatRepository;

    @Override
    public AirplaneResponse getById(Long id) {
        log.info("Started the getById method with id = " + id);
        AirplaneResponse airplaneResponse = airplaneRepository.findById(id).
                map(airplaneMapper::toAirplaneDto).orElseThrow(() -> new AirplaneNotFoundException(" Airplane with id " + id));
        log.info("getById method is done");
        return airplaneResponse;
    }

    @Override
    public Page<AirplaneResponse> getAll(Pageable pageable) {
        log.info("getAll method is started with pagination");
        Page<Airplane> airplanePage = airplaneRepository.findAll(pageable);
        log.info("getAll method is done with pagination");
        return airplanePage.map(airplaneMapper::toAirplaneDto);
    }

    @Override
    public AirplaneResponse add(AirplaneRequestDto airplaneRequestDto) {
        log.info("Add method for airplane is started");
        Airplane airplane = airplaneMapper.toAirplane(airplaneRequestDto);
        airplane.getSeat().forEach(it -> it.setAirplane(airplane));
        Airplane savedAirplane = airplaneRepository.save(airplane);
        log.info("Airplane added with id = {}", savedAirplane.getId());
        return airplaneMapper.toAirplaneDto(savedAirplane);
    }

    @Override
    @Transactional
    public void update(Long id, AirplaneUpdateDto airplaneUpdateDto) {
        log.info("Update method for airplane with id = {} is started", id);
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> new AirplaneNotFoundException("Airplane not found with id " + id));
        log.info("Airplane found with id = {}", id);

        if (airplaneUpdateDto.getSeat() != null) {
            log.info("Updating seats for airplane with id = {}", id);
            List<Seat> seatList = new ArrayList<>();
            for (SeatRequest seatRequest : airplaneUpdateDto.getSeat()) {
                Seat seat = seatRepository.findById(seatRequest.getId())
                        .orElseThrow(() -> new SeatNotFoundException("Seat not found with id " + seatRequest.getId()));
                seatMapper.updateSeatFromDto(seat, seatRequest);
                seatList.add(seat);
            }
            seatList.forEach(it -> it.setAirplane(airplane));
            List<Seat> savedList = seatRepository.saveAll(seatList);
            airplane.setSeat(savedList);
            log.info("Seats updated for airplane with id = {}", id);
        }

        airplaneMapper.updateAirplaneFromDto(airplane, airplaneUpdateDto);
        Airplane updatedAirplane = airplaneRepository.save(airplane);
        log.info("Airplane with id = {} updated successfully", updatedAirplane.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting airplane with id = {}", id);
        if (!airplaneRepository.existsById(id)) {
            throw new AirplaneNotFoundException("Airplane with id " + id);
        }
        airplaneRepository.deleteById(id);
        log.info("Airplane with id = {} deleted successfully", id);
    }
}
