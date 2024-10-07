package com.example.skyWardWingss.service.impl;


import com.example.skyWardWingss.dao.entity.*;
import com.example.skyWardWingss.dao.repository.*;
//import com.example.skyWardWingss.entity.*;
import com.example.skyWardWingss.model.enums.PaymentStatus;
import com.example.skyWardWingss.model.enums.ReservationStatus;
import com.example.skyWardWingss.model.enums.SeatStatus;
import com.example.skyWardWingss.model.exceptions.child.CompanyNotFoundException;
import com.example.skyWardWingss.model.exceptions.child.ReservationNotFoundException;
import com.example.skyWardWingss.model.exceptions.child.SeatNotFoundException;
import com.example.skyWardWingss.mapper.PaymentMapper;
import com.example.skyWardWingss.model.dto.request.PaymentRequestDto;
import com.example.skyWardWingss.model.dto.response.PaymentResponse;
//import com.example.skyWardWingss.dao.repository.*;
import com.example.skyWardWingss.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentMapper paymentMapper;
    private final SeatRepository seatRepository;
    private final CompanyRepository companyRepository;
    private final CardDetailRepository cardDetailRepository;

    @Override
    @Transactional
    public PaymentResponse pay(PaymentRequestDto paymentRequestDto) {
        try {
            log.info("Processing payment for reservation ID: {}", paymentRequestDto.getReservationId());
            Reservation reservation = reservationRepository.findById(paymentRequestDto.getReservationId())
                    .orElseThrow(() -> new ReservationNotFoundException("Reservation with id :" + paymentRequestDto.getReservationId()));

            Payment payment = paymentMapper.toPayment(paymentRequestDto);

            Company company = companyRepository.findById(paymentRequestDto.getCompanyId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company with id: " + paymentRequestDto.getCompanyId()));

            payment.setReservation(reservation);
            payment.setCompany(company);

            CardDetail cardDetail = paymentMapper.toCardDetail(paymentRequestDto.getCardDetailedDto());
            cardDetail.setCustomer(reservation.getCustomer());
            cardDetailRepository.save(cardDetail);
            payment.setCardDetail(cardDetail);

            if (isValidCardDetail(paymentRequestDto)) {
                payment.setPaymentStatus(PaymentStatus.COMPLETED);
            } else {
                payment.setPaymentStatus(PaymentStatus.FAILED);
            }

            Payment savedPayment = paymentRepository.save(payment);

            PaymentStatus paymentStatus = savedPayment.getPaymentStatus();
            if (paymentStatus == PaymentStatus.COMPLETED) {
                reservation.setReservationStatus(ReservationStatus.CONFIRMED);
                reservation.setPrice(paymentRequestDto.getAmount());
                updateSeatStatus(reservation, SeatStatus.RESERVED);
            } else {

                reservation.setReservationStatus(ReservationStatus.CANCELED);
                updateSeatStatus(reservation, SeatStatus.UNRESERVED);
            }

            reservationRepository.save(reservation);
            return paymentMapper.toPaymentDto(savedPayment);
        } catch (Exception e) {
            log.error("An unexpected error occurred during payment: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred during payment");
        }
    }

    private boolean isValidCardDetail(PaymentRequestDto paymentRequestDto) {
        return paymentRequestDto.getCardDetailedDto() != null
                && paymentRequestDto.getCardDetailedDto().getExpiryDate().isAfter(LocalDate.now())
                && isValidCvv(paymentRequestDto.getCardDetailedDto().getCvv())
                && isValidBankAccount(paymentRequestDto.getCardDetailedDto().getBankAccount())
                && paymentRequestDto.getCardDetailedDto().getCustomerFullName() != null;
    }

    private boolean isValidCvv(Integer cvv) {
        return cvv != null && cvv.toString().length() == 3;
    }

    private boolean isValidBankAccount(String bankAccount) {
        return bankAccount != null && bankAccount.length() == 16 && bankAccount.matches("\\d+");
    }

    private void updateSeatStatus(Reservation reservation, SeatStatus seatStatus) {
        log.info("Updating seat status for seat number: {} on airplane: {}", reservation.getSeatNumber(), reservation.getFlight().getAirplane().getId());
        Seat seat = seatRepository.findFirstBySeatNumberAndAirplane(reservation.getSeatNumber(), reservation.getFlight().getAirplane())
                .orElseThrow(() -> new SeatNotFoundException("Seat"));
        seat.setSeatStatus(seatStatus);
        seatRepository.save(seat);

    }
}
