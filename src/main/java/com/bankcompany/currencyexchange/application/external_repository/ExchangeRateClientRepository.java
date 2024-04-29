package com.bankcompany.currencyexchange.application.external_repository;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ExchangeRateClientRepository {
    Mono<BigDecimal> getExchangeRate(String originalCurrency, String targetCurrency);
}
