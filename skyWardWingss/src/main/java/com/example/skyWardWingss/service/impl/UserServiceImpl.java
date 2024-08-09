package com.example.skyWardWingss.service.impl;

import com.example.skyWardWingss.model.exceptions.child.PasswordWrongException;
import com.example.skyWardWingss.model.exceptions.child.UserNotFoundException;
import com.example.skyWardWingss.model.dto.request.ChangePasswordDto;
import com.example.skyWardWingss.dao.entity.User;
import com.example.skyWardWingss.dao.entity.Customer;
import com.example.skyWardWingss.dao.repository.CustomerRepository;
import com.example.skyWardWingss.dao.repository.UserRepository;
import com.example.skyWardWingss.service.UserService;
import com.example.skyWardWingss.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<String> changePassword(HttpServletRequest request, ChangePasswordDto changePasswordDto) {
        try {
            Long userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
            log.info("user changePassword method started by userId: {}", userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(UserNotFoundException::new);
            if (changePasswordDto.getNewPassword().equals(changePasswordDto.getRetryPassword()) &&
                    passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                userRepository.save(user);
                log.info("Password changed by user_id = {}", userId);
                return ResponseEntity.ok("Password changed successfully");
            } else {
                log.error("failed to change password");
                throw new PasswordWrongException();
            }
        } catch (Exception e) {
            log.error("Error changing password: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Failed to change password: " + e.getMessage());
        }
    }

    @Override
    public User findByEmail(String email) {
        return Optional.ofNullable(customerRepository.findByEmail(email))
                .map(Customer::getUser)
                .orElseThrow(UserNotFoundException::new);
    }
}
