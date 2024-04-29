package com.bankcompany.currencyexchange.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    public static final String COMMA = ",";
    @Value("${spring.cors-properties.cors.allowed-methods}")
    private String allowedMethods;

    @Value("${spring.cors-properties.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${spring.cors-properties.cors.allowed-headers}")
    private String allowedHeaders;

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        var corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedMethods(Arrays.asList(allowedMethods.split(COMMA)));
        corsConfig.setAllowedOrigins(Arrays.asList(allowedOrigins.split(COMMA)));
        corsConfig.setAllowedHeaders(Arrays.asList(allowedHeaders.split(COMMA)));
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        return new CorsWebFilter(corsConfiguration());
    }
}