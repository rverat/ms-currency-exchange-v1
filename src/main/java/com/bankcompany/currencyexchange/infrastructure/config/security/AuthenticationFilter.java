package com.bankcompany.currencyexchange.infrastructure.config.security;


import com.bankcompany.currencyexchange.infrastructure.config.security.model.AuthenticationTokenData;
import com.bankcompany.currencyexchange.infrastructure.exception.types.NotAuthorizedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        log.info("AuthenticationFilter:: filter:: reached {}", System.currentTimeMillis());

        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (AuthenticationTokenData) securityContext.getAuthentication().getPrincipal())
                .defaultIfEmpty(AuthenticationTokenData.builder().build())
                .flatMap(token -> {
                    if (token.getUserAudience() != null) {
                        return Mono.error(new NotAuthorizedException("User not authorized"));
                    }
                    return chain.filter(exchange);
                });
    }
}