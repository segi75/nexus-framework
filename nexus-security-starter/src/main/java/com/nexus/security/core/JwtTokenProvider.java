package com.nexus.security.core;

import java.nio.charset.StandardCharsets;
import java.util.Collection; // [추가]
import java.util.Date;
import java.util.stream.Collectors; // [추가]

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority; // [추가]
import org.springframework.security.core.authority.SimpleGrantedAuthority; // [추가]
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nexus.security.dto.NexusUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    // ... (이전 코드와 동일: 멤버변수, getKey, createToken) ...
    @Value("${nexus.jwt.secret:nexus-framework-dev-secret-key-must-be-very-long-for-security}")
    private String secretKey;

    @Value("${nexus.jwt.expiration:3600000}")
    private long validityInMilliseconds;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(NexusUser user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .subject(user.getId())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .claim("dept", user.getDeptCode())
                .issuedAt(now)
                .expiration(validity)
                .signWith(getKey())
                .compact();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        NexusUser principal = new NexusUser(
                claims.getSubject(),
                claims.get("name", String.class),
                claims.get("role", String.class),
                claims.get("dept", String.class)
        );

        // [Fix] List<String> -> List<SimpleGrantedAuthority> 변환
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
   

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            log.debug("Invalid JWT Token: {}", e.getMessage());
            return false;
        }
    }

    private Claims parseClaims(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token);
        return jws.getPayload();
    }
}