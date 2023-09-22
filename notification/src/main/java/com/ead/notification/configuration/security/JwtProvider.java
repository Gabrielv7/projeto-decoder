package com.ead.notification.configuration.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    Logger log = LogManager.getLogger(JwtProvider.class.getName());

    private static final String INVALID_JWT_SIGNATURE = "Invalid JWT signature: {}";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token: {}";
    public static final String JWT_TOKEN_IS_EXPIRED = "JWT token is expired: {}";
    public static final String JWT_TOKEN_IS_UNSUPPORTED = "JWT token is unsupported: {}";
    public static final String JWT_CLAIMS_STRING_IS_EMPTY = "JWT claims string is empty: {}";

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;

    public String getSubjectJwt(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getClaimNameJwt(String token, String claimName) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(claimName).toString();
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
