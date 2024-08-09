package com.example.skyWardWingss.service;

import com.example.skyWardWingss.dao.entity.User;
import com.example.skyWardWingss.model.dto.request.ChangePasswordDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    void changePassword(User user, String newPassword);
    ResponseEntity<String> changePassword(HttpServletRequest request, ChangePasswordDto changePasswordDto);
    User findByEmail(String email);
}
