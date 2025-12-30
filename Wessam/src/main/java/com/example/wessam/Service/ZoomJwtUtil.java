package com.example.wessam.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ZoomJwtUtil {

    private String apiKey = "YOUR_ZOOM_API_KEY";
    private String apiSecret = "YOUR_ZOOM_API_SECRET";

    public String generateToken() {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 3600000; // 1 hour validity

        return Jwts.builder()
                .setIssuer(apiKey)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .signWith(SignatureAlgorithm.HS256, apiSecret.getBytes())
                .compact();
    }
}