package com.example.skyWardWingss.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequest {
    @NotBlank
    @Email
    private String receiver;
    @NotBlank
    private String subject;
    @NotBlank
    private String text;

}
