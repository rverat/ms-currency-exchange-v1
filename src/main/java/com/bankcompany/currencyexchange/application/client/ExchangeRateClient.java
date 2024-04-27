package com.bankcompany.currencyexchange.application.client;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ExchangeRateClient {
    Mono<BigDecimal> getExchangeRate(String originalCurrency, String targetCurrency);
}
