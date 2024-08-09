package com.example.skyWardWingss.service.impl;


import com.example.skyWardWingss.dao.entity.Airplane;
import com.example.skyWardWingss.dao.entity.Flight;
import com.example.skyWardWingss.dao.entity.TypePrice;
import com.example.skyWardWingss.model.exceptions.child.AirplaneNotFoundException;
import com.example.skyWardWingss.model.exceptions.child.FlightNotFoundException;
import com.example.skyWardWingss.mapper.FlightMapper;
import com.example.skyWardWingss.mapper.TypePriceMapper;
import com.example.skyWardWingss.model.dto.request.FlightRequestDto;
import com.example.skyWardWingss.model.dto.request.FlightUpdateDto;
import com.example.skyWardWingss.model.dto.response.FlightResponse;
import com.example.skyWardWingss.dao.repository.AirplaneRepository;
import com.example.skyWardWingss.dao.repository.FlightRepository;
import com.example.skyWardWingss.dao.repository.TypePriceRepository;
import com.example.skyWardWingss.service.FlightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;
    private final TypePriceRepository typePriceRepository;
    private final FlightMapper flightMapper;
    private final TypePriceMapper typePriceMapper;

    @Override
    @Transactional
    public List<FlightResponse> searchFlight(String departure, String destination, LocalDate departureTime) {
        log.info("Searching flights from {} to {} on {}", departure, destination, departureTime);
        LocalDateTime startOfDay = departureTime.atStartOfDay();
        LocalDateTime endOfDay = departureTime.atTime(23, 59, 59);

        List<Flight> flights = flightRepository.findByDepartureAndDestinationAndDepartureTimeBetween(departure, destination, startOfDay, endOfDay);
        return flights.stream().map(flightMapper::toFlightDto)
                .toList();
    }

    @Override
    public FlightResponse getById(Long id) {
        log.info("Started the getById method with id = " + id);
        FlightResponse flightResponse = flightRepository.findById(id).
                map(flightMapper::toFlightDto).orElseThrow(() -> new FlightNotFoundException("Flight with id " + id));
        log.info("getById method is done");
        return flightResponse;
    }

    @Override
    public Page<FlightResponse> getAll(Pageable pageable) {
        log.info("getAll method is started");
        Page<Flight> flightPage = flightRepository.findAll(pageable);
        log.info("getAll method is done");
        return flightPage.map(flightMapper::toFlightDto);
    }

    @Override
    @Transactional
    public FlightResponse add(FlightRequestDto flightRequestDto) {
        log.info("Adding new flight with details: {}", flightRequestDto);
        Airplane airplane = airplaneRepository.findById(flightRequestDto.getAirplaneId())
                .orElseThrow(() -> new AirplaneNotFoundException("Airplane not found"));

        Flight flight = flightMapper.toFlight(flightRequestDto);
        flight.setAirplane(airplane);

        List<TypePrice> typePrices = flightRequestDto.getTypePrice().stream()
                .map(dto -> new TypePrice(flight, dto.getPrice(), dto.getType()))
                .collect(Collectors.toList());

        flight.setTypePrice(typePrices);

        Flight savedFlight = flightRepository.save(flight);
        log.info("Flight added successfully");
        return flightMapper.toFlightDto(savedFlight);
    }


    @Override
    @Transactional
    public void update(Long id, FlightUpdateDto flightUpdateDto) {
        log.info("Updating flight with id {} with details: {}", id, flightUpdateDto);
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id " + id));

        if (flightUpdateDto.getTypePrice() != null) {
            List<TypePrice> typePriceList = flightUpdateDto.getTypePrice().stream()
                    .map(typePriceUpdateDto -> {
                        if (typePriceUpdateDto.getId() != null) {
                            return typePriceRepository.findById(typePriceUpdateDto.getId())
                                    .map(existingTypePrice -> {
                                        typePriceMapper.updateTypePriceFromDto(existingTypePrice, typePriceUpdateDto);
                                        return existingTypePrice;
                                    })
                                    .orElseGet(() -> typePriceMapper.totypePrice(typePriceUpdateDto));
                        } else {
                            return typePriceMapper.totypePrice(typePriceUpdateDto);
                        }
                    })
                    .collect(Collectors.toList());
            flight.setTypePrice(typePriceList);
        }

        flightMapper.updateFlightFromDto(flight, flightUpdateDto);
        flightRepository.save(flight);
        log.info("Flight updated successfully: {}", flight);
    }


    @Override
    public void delete(Long id) {
        log.info("Deleting flight with id = {}", id);
        if (!flightRepository.existsById(id)) {
            throw new FlightNotFoundException("Flight with id " + id);
        }
        flightRepository.deleteById(id);
        log.info("Flight deleted successfully");
    }
}
