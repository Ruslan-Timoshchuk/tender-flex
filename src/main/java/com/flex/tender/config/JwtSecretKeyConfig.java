package com.flex.tender.config;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtSecretKeyConfig {

    @Value("${jwt.access.signing.key}")
    private String jwtAccessSigningKey;
    
    @Bean
    public SecretKey accessTokenSecretKey() {
        return Keys
                 .hmacShaKeyFor(toBytes(jwtAccessSigningKey));
    }
    
    private byte[] toBytes(String signingKey) {
        byte[] keyBytes = Decoders
                            .BASE64
                            .decode(signingKey);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT signing key must be at least 256 bits");
        }
        return keyBytes;
    }
    
}