package com.ofg.qa.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${qa.secret}")
    private String secret;

    @Value("${qa.expires_in}")
    private long expriesIn;

    public String generateJwtToken(Authentication authentication) {
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + expriesIn);

        return Jwts.builder().setSubject(Long.toString(currentUser.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Long getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return !isTokenExpired(authToken);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
