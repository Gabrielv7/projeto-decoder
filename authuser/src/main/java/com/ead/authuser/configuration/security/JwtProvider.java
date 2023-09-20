package com.ead.authuser.configuration.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtProvider {

    private static final String INVALID_JWT_SIGNATURE = "Invalid JWT signature: {}";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token: {}";
    public static final String JWT_TOKEN_IS_EXPIRED = "JWT token is expired: {}";
    public static final String JWT_TOKEN_IS_UNSUPPORTED = "JWT token is unsupported: {}";
    public static final String JWT_CLAIMS_STRING_IS_EMPTY = "JWT claims string is empty: {}";

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;

    @Value("${ead.auth.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwt(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        final String roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(String.valueOf(userDetails.getUserId()))
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(generatedTimeExpiration())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private Date generatedTimeExpiration() {
        return new Date((new Date()).getTime() + jwtExpirationMs);
    }

    public String getSubjectJwt(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwt(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error(INVALID_JWT_SIGNATURE, e.getMessage());
        } catch (MalformedJwtException e) {
            log.error(INVALID_JWT_TOKEN, e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(JWT_TOKEN_IS_EXPIRED, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(JWT_TOKEN_IS_UNSUPPORTED, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(JWT_CLAIMS_STRING_IS_EMPTY, e.getMessage());
        }
        return false;
    }

}
