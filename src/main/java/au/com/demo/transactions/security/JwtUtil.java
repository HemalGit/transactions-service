package au.com.demo.transactions.security;

import au.com.demo.transactions.model.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtUtil {

    private final String secretKey;

    @Value("${application.security.jwt.expiry}")
    private long expireIn;

    private final JwtParser jwtParser;
    private static final String AUTH_TOKEN_HEADER = "Authorization";
    private static final String AUTH_TOKEN_PREFIX = "Bearer ";

    public JwtUtil(@Value("${application.security.jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
        this.jwtParser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();
    }

    public String createToken(UserDto user) {
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(expireIn));

        Claims claims = Jwts.claims()
                .subject(user.getEmail())
                .add("roles", user.getRoles())
                .expiration(tokenValidity)
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTH_TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(AUTH_TOKEN_PREFIX)) {
            return bearerToken.substring(AUTH_TOKEN_PREFIX.length());
        }
        return null;
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            log.error("Error validating token expiry");
            throw ex;
        }
    }
}
