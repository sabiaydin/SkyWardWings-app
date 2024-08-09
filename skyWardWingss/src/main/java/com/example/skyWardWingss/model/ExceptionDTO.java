package com.example.skyWardWingss.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExceptionDTO {
    private int statusCode;
    private String message;
    private Map<String, String> errors;


    public ExceptionDTO(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors=Map.of();
    }
}
