package com.example.skyWardWingss.controller;

import com.example.skyWardWingss.model.exceptions.InvalidFlightIdException;
import com.example.skyWardWingss.model.exceptions.SeatAlreadyReservedException;
import com.example.skyWardWingss.model.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error("Validation error occurred: {}", errors);

        ExceptionDTO exceptionDTO = new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                errors
        );

        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(InvalidFlightIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleInvalidFlightIdException(InvalidFlightIdException ex) {
        log.error("Invalid flight ID: {}", ex.getMessage());

        return new ExceptionDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                Map.of()
        );
    }

    @ExceptionHandler(SeatAlreadyReservedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDTO handleSeatAlreadyReservedException(SeatAlreadyReservedException ex) {
        log.error("Seat is already reserved: {}", ex.getMessage());

        return new ExceptionDTO(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                Map.of()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage());

        return new ExceptionDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                Map.of()
        );
    }
}
