package com.example.skyWardWingss.service.impl;


import com.example.skyWardWingss.dao.entity.*;
import com.example.skyWardWingss.dao.repository.*;
import com.example.skyWardWingss.entity.*;
import com.example.skyWardWingss.model.exceptions.InvalidFlightIdException;
import com.example.skyWardWingss.model.exceptions.SeatAlreadyReservedException;
import com.example.skyWardWingss.model.exceptions.child.ReservationNotFoundException;
import com.example.skyWardWingss.mapper.CustomerMapper;
import com.example.skyWardWingss.mapper.ReservationMapper;
import com.example.skyWardWingss.model.dto.request.ReservationRequestDto;
import com.example.skyWardWingss.model.dto.response.ReservationResponse;
import com.example.skyWardWingss.repository.*;
import com.example.skyWardWingss.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final ReservationMapper reservationMapper;
    private final SeatRepository seatRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final TypePriceRepository priceRepository;

    @Override
    @Transactional
    public ReservationResponse reserveFlight(ReservationRequestDto reservationRequestDto) {
        log.info("Attempting to reserve flight with details: {}", reservationRequestDto);
        try {
            Customer customer = customerRepository.findByEmail(reservationRequestDto.getCustomerRequestDto().getEmail());
            boolean isLoggedInUser = customer != null && customer.getEmail().equals(reservationRequestDto.getCustomerRequestDto().getEmail());
            if (customer == null) {
                customer = customerRepository.save(customerMapper.toCustomer(reservationRequestDto.getCustomerRequestDto()));
            }
            Flight flight = flightRepository.findById(reservationRequestDto.getFlightId())
                    .orElseThrow(() -> new InvalidFlightIdException("Invalid flight ID"));
            if (isSeatReserved(reservationRequestDto.getSeatNumber(), flight)) {
                throw new SeatAlreadyReservedException("Seat is already reserved");
            }
            TypePrice price = priceRepository.findByFlightIdAndType(reservationRequestDto.getFlightId(),
                    reservationRequestDto.getSeatType());
            Seat seat = new Seat();
            seat.setSeatNumber(reservationRequestDto.getSeatNumber());
            seat.setSeatType(reservationRequestDto.getSeatType());
            seat.setAirplane(flight.getAirplane());
            if (isLoggedInUser) {
                long reservationCount = reservationRepository.countByCustomerAndFlight(customer, flight);
                if (reservationCount >= 3) {
                    seat.setPrice((price.getPrice() * 0.9));
                }
            } else {
                seat.setPrice(Double.valueOf(price.getPrice()));
            }
            Seat savedSeat = seatRepository.save(seat);
            log.info("Seat saved successfully: {}", savedSeat);
            Reservation reservation = reservationMapper.toReservation(reservationRequestDto);
            reservation.setCustomer(customer);
            reservation.setFlight(flight);
            reservation.setPrice(savedSeat.getPrice());
            Reservation savedReservation = reservationRepository.save(reservation);
            log.info("Reservation saved successfully: {}", savedReservation);
            return reservationMapper.toReservationDto(savedReservation);
        } catch (InvalidFlightIdException | SeatAlreadyReservedException exception) {
            throw exception;
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred");
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting reservation with ID: {}", id);
        reservationRepository.deleteById(id);
        log.info("Reservation deleted successfully");
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        log.info("Fetching reservation with ID: {}", id);
        return reservationRepository.findById(id)
                .map(reservationMapper::toReservationDto).orElseThrow(
                        () -> new ReservationNotFoundException("Reservation with id " + id));
    }

    @Override
    public Page<ReservationResponse> getAll(Pageable pageable) {
        log.info("getAll method is started");
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        log.info("getAll method is done");
        return reservationPage.map(reservationMapper::toReservationDto);
    }

    private boolean isSeatReserved(String seatNumber, Flight flight) {
        return reservationRepository.existsBySeatNumberAndFlight(seatNumber, flight);
    }
}

