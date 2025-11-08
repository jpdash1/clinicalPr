package com.clinic.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // TODO: load from application properties or environment variable in production
    private static final String SECRET = "CHANGE_THIS_TO_A_LONG_RANDOM_SECRET_KEY_32+_BYTES!";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long DEFAULT_EXP_SECONDS = 3600; // 1 hour

    public static String generate(String doctorId, String mobile, long expSeconds) {
        long now = System.currentTimeMillis();
        long exp = (expSeconds <= 0 ? DEFAULT_EXP_SECONDS : expSeconds) * 1000;
        return Jwts.builder()
                .setSubject("doctor")
                .claim("doctorId", doctorId)
                .claim("mobile", mobile)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + exp))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims verify(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
