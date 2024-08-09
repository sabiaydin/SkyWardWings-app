package com.example.skyWardWingss.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @Size(min = 3, max = 30)
    @NotNull(message = "current password can not be null")
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "Invalid password input type")
    private String currentPassword;

    @NotNull(message = "new password can not be null")
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "Invalid password input type")
    private String newPassword;

    @NotNull(message = "retry password can not be null")
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "Invalid password input type")
    private String retryPassword;
}
