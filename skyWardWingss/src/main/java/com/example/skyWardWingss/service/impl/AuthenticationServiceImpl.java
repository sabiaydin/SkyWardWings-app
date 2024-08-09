package com.example.skyWardWingss.service.impl;

import com.example.skyWardWingss.dao.entity.Authority;
import com.example.skyWardWingss.dao.entity.Customer;
import com.example.skyWardWingss.dao.entity.PasswordResetToken;
import com.example.skyWardWingss.dao.entity.User;
import com.example.skyWardWingss.model.exceptions.child.PasswordMismatchException;
import com.example.skyWardWingss.mapper.CustomerMapper;
import com.example.skyWardWingss.mapper.UserMapper;
import com.example.skyWardWingss.model.dto.request.EmailRequest;
import com.example.skyWardWingss.model.dto.request.LoginRequestDto;
import com.example.skyWardWingss.model.dto.request.RecoveryPassword;
import com.example.skyWardWingss.model.dto.request.UserRequest;
import com.example.skyWardWingss.model.dto.response.ErrorResponse;
import com.example.skyWardWingss.model.dto.response.LoginResponse;
import com.example.skyWardWingss.dao.repository.AuthorityRepository;
import com.example.skyWardWingss.dao.repository.CustomerRepository;
import com.example.skyWardWingss.dao.repository.PasswordResetTokenRepository;
import com.example.skyWardWingss.dao.repository.UserRepository;
import com.example.skyWardWingss.service.AuthenticationService;
import com.example.skyWardWingss.service.EmailSenderService;
import com.example.skyWardWingss.service.UserService;
import com.example.skyWardWingss.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;
    private final AuthorityRepository authorityRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailSenderService emailSenderService;
    private final UserService userService;

    @Override
    @Transactional
    public ResponseEntity<?> authenticate(LoginRequestDto loginRequestDto) {
        log.info("authenticate method started by: {}", loginRequestDto.getUsername());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()));
            log.info("authentication details: {}", authentication);
            String username = authentication.getName();
            User user = new User(username, "");
            String token = jwtUtil.createToken(user);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            LoginResponse loginRes = new LoginResponse(username, token);
            log.info("user: {} logged in", user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginRes);

        } catch (BadCredentialsException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid username or password");
            log.error("Error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            log.error("Error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @Override
    @Transactional
    public void register(UserRequest userRequest) {
        Customer customer = customerMapper.toCustomer(userRequest.getCustomerRequestDto());
        if (customerRepository.existsByEmail(customer.getEmail())) {
            customer = customerRepository.findByEmail(customer.getEmail());
        } else {
            customer = customerRepository.save(customer);
        }
        User user = userMapper.toUser(userRequest);
        user.setCustomer(customer);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Authority authority = authorityRepository.findByName(userRequest.getRole());
        Set<Authority> authorities = new HashSet<>(Set.of(authority));
        user.setAuthorities(authorities);
        userRepository.save(user);
    }


    @Override
    public ResponseEntity<String> requestPasswordReset(String email) {
        log.info("requestPasswordReset method started by: {}", email);
        User user = userService.findByEmail(email);
        if (user == null) {
            log.info("user is null for requestPasswordReset method");
            return ResponseEntity.badRequest().body("User not found with this email!");
        }
        Long userId = user.getId();
        Optional<PasswordResetToken> passwordResetToken = tokenRepository.findByUserId(userId);
        passwordResetToken.ifPresent(tokenRepository::delete);
        String newToken = generateRandomToken();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        Date expiryDate = calendar.getTime();

        EmailRequest receiverEmail = new EmailRequest();
        receiverEmail.setReceiver(email);
        receiverEmail.setText(newToken);
        receiverEmail.setSubject("SkyWardWings - recovery password");
        try {
            emailSenderService.sendSimpleEmail(receiverEmail);
            createToken(user, newToken, expiryDate);
        } catch (Exception e) {
            log.error("Error due to: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Email couldn't sent. Try again.");
        }
        log.info("token sent with email for recovery password to {}", email);
        return ResponseEntity.ok("Ok. Verify token was sent to your email");
    }

    @Override
    public ResponseEntity<String> resetPassword(RecoveryPassword recoveryPassword) {
        log.info("resetPassword method started by token: {}", recoveryPassword.getToken());
        if (recoveryPassword.getNewPassword().equals(recoveryPassword.getRetryPassword())) {
            PasswordResetToken passwordResetToken = tokenRepository.findByToken(recoveryPassword.getToken());
            if (!isTokenValid(passwordResetToken)) {
                log.info("Token is not valid");
                return ResponseEntity.badRequest().body("Ops! Something went wrong!");
            }
            User user = passwordResetToken.getUser();
            userService.changePassword(user, passwordEncoder.encode(recoveryPassword.getNewPassword()));
            log.info("password changed for userId: {}", user.getId());
            deleteToken(passwordResetToken);
            log.info("Password successfully reset by token: {}", recoveryPassword.getToken());
            return ResponseEntity.ok("Password reset successfully!");
        } else
            log.error("passwords entered do not match");
        throw new PasswordMismatchException();
    }
    private boolean isTokenValid(PasswordResetToken token) {
        return token != null && !token.getExpiryDate().before(new Date());
    }

    private void deleteToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }

    private String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        int TOKEN_LENGTH = 32;
        byte[] bytes = new byte[TOKEN_LENGTH / 2];
        random.nextBytes(bytes);
        return new BigInteger(1, bytes).toString(16);
    }

    private void createToken(User user, String token, Date expiryDate) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(expiryDate);
        tokenRepository.save(passwordResetToken);
        log.info("Token created for forgot password function");
    }
}
