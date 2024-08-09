package com.example.skyWardWingss.model.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardDetailedDto {
    private String CustomerFullName;
    private String bankAccount;
    private Integer cvv;
    private LocalDate expiryDate;
}
