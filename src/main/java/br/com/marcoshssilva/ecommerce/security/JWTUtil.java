package br.com.marcoshssilva.ecommerce.security;


import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
    }
    
    public Boolean tokenValido(String token) {
        DecodedJWT claims = getClaims(token);
        
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiresAt();
            Date now = new Date(System.currentTimeMillis());

            return username != null && expirationDate != null && now.before(expirationDate);
        }
        return false;
    }

    public String getUsername(String token) {
        DecodedJWT claims = getClaims(token);
        if (claims != null) return claims.getSubject();
        return null;
    }
    
    private DecodedJWT getClaims(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
    
}
