package com.example.Skool.auth.services;

import com.example.Skool.auth.entities.UserPrincipal;
import com.example.Skool.common.constants.AppConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final Environment env;

    public String createJWT(UserPrincipal userPrincipal) {
        String secret = env.getProperty(AppConstants.JWT_SECRET, "6b5c416f362963e3850f84d57d18ffed0fc91ad8");
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .subject(userPrincipal.getName())
                .claim("id", userPrincipal.getId())
                .issuedAt(new Date())
                .signWith(secretKey)
                .expiration(new Date((new Date()).getTime() + 24 * 60 * 60 * 1000))
                .compact();
    }
}
