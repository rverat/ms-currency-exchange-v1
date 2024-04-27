package com.bankcompany.currencyexchange.infrastructure.config.security.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.bankcompany.currencyexchange.infrastructure.config.security.util.JwtAlgorithmManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.bankcompany.currencyexchange.infrastructure.config.security.util.EncryptionUtility.PRIVATE_KEY;
import static com.bankcompany.currencyexchange.infrastructure.config.security.util.EncryptionUtility.PUBLIC_KEY;

@Configuration
public class AppConfig {

    @Bean
    public Algorithm getTokenAlgorithm() {
        var helper = new JwtAlgorithmManager(PUBLIC_KEY, PRIVATE_KEY);
        return helper.getTokenAlgorithm();
    }
}
