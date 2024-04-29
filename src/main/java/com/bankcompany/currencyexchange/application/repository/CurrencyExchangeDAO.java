package com.bankcompany.currencyexchange.application.repository;

import com.bankcompany.currencyexchange.domain.model.CurrencyExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyExchangeDAO {
    Flux<CurrencyExchange> findAllCurrencyExchange();

    Mono<CurrencyExchange> saveCurrencyExchange(CurrencyExchange currencyExchange);

    Mono<CurrencyExchange> updateCurrencyExchange(CurrencyExchange currencyExchange);

    Mono<Void> deleteCurrencyExchange(CurrencyExchange currencyExchange);
}
