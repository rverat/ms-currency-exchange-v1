package com.bankcompany.currencyexchange.domain.service;

import com.bankcompany.currencyexchange.interfaces.dto.CurrencyExchangeDto;
import com.bankcompany.currencyexchange.interfaces.dto.ExchangeRateRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyExchangeService {

    Mono<CurrencyExchangeDto> applyExchangeRate(ExchangeRateRequestDto requestDto);

    Flux<CurrencyExchangeDto> getAllMovementExchangeRate();

}
