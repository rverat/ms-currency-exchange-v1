package com.bankcompany.currencyexchange.infrastructure.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bankcompany.currencyexchange.infrastructure.config.security.util.JwtUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class JwtService {

    private final Algorithm algorithm;

    public Mono<DecodedJWT> verifyToken(String token) {
        try {
            return Mono.just(JWT.require(algorithm)
                            .withIssuer(JwtUtility.TOKEN_ISSUER)
                            .build())
                    .map(verifier -> verifier.verify(token));
        } catch (SignatureVerificationException
                 | AlgorithmMismatchException
                 | TokenExpiredException
                 | InvalidClaimException e) {
            return Mono.error(() -> new RuntimeException("Token Verification Failed - {}", e));
        }
    }
}
