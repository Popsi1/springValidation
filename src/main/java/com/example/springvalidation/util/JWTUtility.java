package com.example.springvalidation.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtility implements Serializable {

        private static final long serialVersionUID = 234234523523L;

        private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

        @Value("${jwt.secret}")
        private String secret;

        public String getUsernameFromToken(String token) {
                return getClaimFromToken(token, Claims::getSubject);
        }

        public String generateToken(String username) {
                Map<String, Object> claims = new HashMap<>();
                return doGenerateToken(claims, username);
        }

        private String doGenerateToken(Map<String, Object> claims, String subject) {
                return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 120000))
                        .signWith(SignatureAlgorithm.HS512, secret).compact();
        }

        public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
                final Claims claims = getAllClaimsFromToken(token);
                return claimsResolver.apply(claims);
        }

        private Claims getAllClaimsFromToken(String token) {
                return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }

        public Boolean isTokenExpired(String token) {
                final Date expiration = getExpirationDateFromToken(token);
                return expiration.before(new Date());
        }

        public Date getExpirationDateFromToken(String token) {
                return getClaimFromToken(token, Claims::getExpiration);
        }

        public Boolean validateToken(String token, String username) {
                final String usernameFromToken = getUsernameFromToken(token);
                return (username.equals(usernameFromToken) && !isTokenExpired(token));
        }
}
