package com.example.skyWardWingss.utility;

import com.example.skyWardWingss.dao.entity.Authority;
import com.example.skyWardWingss.dao.entity.User;
import com.example.skyWardWingss.model.exceptions.child.UserNotFoundException;
import com.example.skyWardWingss.dao.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@PropertySource("classpath:application.yaml")
@RequiredArgsConstructor
public class JwtUtil {
    private final UserRepository userRepository;
    @Value("${security.jwt.secret-key}")
    private String secret_key;
    @Value("${security.jwt.expiration}")
    private long accessTokenValidity;
    private static Key key;

    public Key initializeKey() {
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(secret_key);
        key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    public String createToken(User user) {
        key = initializeKey();
        user = userRepository.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
        Set<Authority> authorities = user.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (Authority authority : authorities) {
            roles.add(authority.getName());
        }
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("authorities", roles);
        claimsMap.put("username", user.getUsername());
        claimsMap.put("user_id", user.getId());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                .addClaims(claimsMap)
                .signWith(key, SignatureAlgorithm.HS512);
        log.info("Jwt token created for user: {}", user.getUsername());
        return jwtBuilder.compact();
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(initializeKey()).build().parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String TOKEN_HEADER = "Authorization";
        String bearerToken = request.getHeader(TOKEN_HEADER);
        String TOKEN_PREFIX = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    public Long getUserId(Claims claims) {
        Object userId=claims.get("user_id");
        if (userId instanceof Integer){
            return ((Integer) userId).longValue();
        } else if (userId instanceof Long) {
            return (Long) userId;
        }else {
            throw new  IllegalArgumentException("Invalid user_id type");
        }
    }

    public Collection<GrantedAuthority> extractAuthorities(Claims claims) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (claims.containsKey("authorities")) {
            Object authoritiesObj = claims.get("authorities");
            if (authoritiesObj instanceof List<?> roles) {
                for (Object role : roles) {
                    if (role instanceof String) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    } else {
                        throw new IllegalArgumentException("Invalid authority type");
                    }
                }
            } else {
                throw new IllegalArgumentException("Authorities claim is not a list");
            }
        }
        return authorities;
    }
}
