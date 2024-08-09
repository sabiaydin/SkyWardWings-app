package com.example.skyWardWingss.utility;

import com.example.skyWardWingss.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null ) {
                filterChain.doFilter(request, response);
                return;
            }
            log.info("JWT token: {} ", accessToken);

            Claims claims = jwtUtil.resolveClaims(request);
            if(claims != null & jwtUtil.validateClaims(claims)){
                String username = claims.getSubject();
                Collection<GrantedAuthority> authorities = jwtUtil.extractAuthorities(claims);
                log.info("Authenticated user: {} with authorities: {}", username, authorities);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(username,"",authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication set in security context for user: {}", username);
            }else {
                log.warn("JWT claims are null or not valid");
            }
        }catch (Exception e){
            log.error("Error due to: {}", e.getClass().getName() + " -> " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
    }

