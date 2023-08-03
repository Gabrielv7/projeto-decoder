package com.ead.authuser.configuration.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log4j2
@Component
public class JwtProvider {

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;

    @Value("${ead.auth.jwtExpirationMs}")
    private String jwtExpirationMs;

    public String generateJwt(Authentication authentication) {
        UserDetails user = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private Date generateExpirationDate() {
        // Converter a string jwtExpirationMs em um valor numérico (long) representando os milissegundos
        long expirationMs = Long.parseLong(jwtExpirationMs);
        // Obter a data atual em milissegundos
        long currentTimeMillis = System.currentTimeMillis();
        // Somar a data atual com o tempo de expiração para obter a data de expiração do JWT
        long expirationTimeMillis = currentTimeMillis + expirationMs;
        // Criar a instância da classe Date com a data de expiração em milissegundos
        return new Date(expirationTimeMillis);
    }

}
