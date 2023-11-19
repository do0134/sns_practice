package com.practice.sns.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.SQLOutput;
import java.util.Date;

public class JwtTokenUtils {

    public static String getUserName(String token, String key) {
        return extractClaims(token,key).get("userName", String.class);
    }

    public static boolean isExpired(String token, String key) {
        Date expired = extractClaims(token, key).getExpiration();
        return expired.before(new Date());
    }

    private static Claims extractClaims(String token, String key) {
        return Jwts.parserBuilder().setSigningKey(getKey(key))
                .build().parseClaimsJws(token).getBody();
    }

    public static String generateToken(String userName, String key, long expiredTimeMS) {
        Claims claims = Jwts.claims();
        claims.put("userName",userName);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMS))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getKey(String key) {
        byte[] keyByte = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
