package com.bankcompany.currencyexchange.infrastructure.config.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bankcompany.currencyexchange.infrastructure.config.security.jwt.JwtService;
import com.bankcompany.currencyexchange.infrastructure.config.security.model.AuthenticationTokenData;
import com.bankcompany.currencyexchange.infrastructure.config.security.util.JwtUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;


@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final JwtService tokenService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.info("AuthenticationManager:: authenticate:: reached {}", System.currentTimeMillis());
        return Mono.just(authentication.getCredentials().toString())
                .flatMap(this::verifyToken)
                .flatMap(this::getAuthentication);
    }

    private Mono<Authentication> getAuthentication(AuthenticationTokenData tokenData) {
        return Mono.just(
                new UsernamePasswordAuthenticationToken(tokenData, null,
                        getGrantedAuthorities())
        );
    }

    private Collection<SimpleGrantedAuthority> getGrantedAuthorities() {
        return List.of(new SimpleGrantedAuthority(DEFAULT_ROLE));
    }

    private Mono<AuthenticationTokenData> verifyToken(String token) {
        return tokenService.verifyToken(token)
                .filter(decodedJWT -> decodedJWT.getAudience() != null && !decodedJWT.getAudience().isEmpty())
                .flatMap(tokenData -> getUser(tokenData, token));
    }

    private Mono<AuthenticationTokenData> getUser(DecodedJWT decodedJWT, String token) {
        return Mono.just(AuthenticationTokenData.builder()
                .userAudience(decodedJWT.getAudience().get(0))
                .userName(decodedJWT.getClaim(JwtUtility.TOKEN_CLAIM_USER).asString())
                .token(token)
                .build()
        );
    }
}