package com.example.skyWardWingss.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequestDto {
    @NotBlank(message = "Username cannot be empty or null")
    @Size(max = 50)
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "Invalid username input type")
    private String username;

    @NotBlank(message = "Password cannot be empty or null")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "Invalid password input type")
    private String password;
}
