package com.example.backend.auth.services.impl;

import java.security.Key;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.backend.auth.services.JwtService;
import com.example.backend.dao.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    //1 Hour po
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;
    //private static final long EXPIRATION_TIME = 1000 * 60;
    //private static final long EXPIRATION_TIME = 1000 * 10;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Override
    public String generateToken(UserEntity user) {
        
        return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .claim("email", user.getEmail())
            .claim("role", user.getRole())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();

    }

    @Override
    public Claims extractAllClaims(String token) {
        
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    @Override
    public String extractUsername(String token) {
        
        return extractAllClaims(token).getSubject();
    }

    @Override
    public String extractRole(String token) {
        
        return extractAllClaims(token).get("role", String.class);
    }

    @Override
    public boolean isTokenExpired(String token) {
        
        return extractAllClaims(token)
            .getExpiration()
            .before(new Date(System.currentTimeMillis()));
    }

    @Override
    public boolean isValid(String token) {
        try{
            extractAllClaims(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}
