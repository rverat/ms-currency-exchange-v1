package com.bankcompany.currencyexchange.domain.service.impl;

import com.bankcompany.currencyexchange.application.client.ExchangeRateClient;
import com.bankcompany.currencyexchange.application.dao.CurrencyExchangeDAO;
import com.bankcompany.currencyexchange.domain.model.CurrencyExchange;
import com.bankcompany.currencyexchange.domain.service.CurrencyExchangeService;
import com.bankcompany.currencyexchange.infrastructure.exception.types.ServiceUnavailableException;
import com.bankcompany.currencyexchange.interfaces.dto.CurrencyExchangeDto;
import com.bankcompany.currencyexchange.interfaces.dto.ExchangeRateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final ExchangeRateClient exchangeRateClient;
    private final CurrencyExchangeDAO exchangeDAO;

    @Override
    public Mono<CurrencyExchangeDto> applyExchangeRate(ExchangeRateRequestDto requestDto) {

        return exchangeRateClient.getExchangeRate(requestDto.currencyFrom(), requestDto.currencyTo())
                .flatMap(exchangeRate -> {
                    var currencyExchange = new CurrencyExchange();
                    currencyExchange.setUserId(requestDto.userId());
                    currencyExchange.setAmount(requestDto.amount());
                    currencyExchange.setConvertedAmount(exchangeRate.multiply(requestDto.amount()));
                    currencyExchange.setCurrencyFrom(requestDto.currencyFrom());
                    currencyExchange.setCurrencyTo(requestDto.currencyTo());
                    currencyExchange.setExchangeRate(exchangeRate);
                    currencyExchange.setDateTime(LocalDateTime.now());

                    return exchangeDAO.saveCurrencyExchange(currencyExchange)
                            .flatMap(this::generateResponse)
                            .onErrorResume(throwable -> {
                                log.error("Error fetching exchange rate cause: {} error message: {}", throwable.getCause(), throwable.getMessage());
                                return Mono.error(
                                        new ServiceUnavailableException("Failed to retrieve currency exchange"));
                            });
                });
    }

    @Override
    public Flux<CurrencyExchangeDto> getAllMovementExchangeRate() {
        return exchangeDAO.findAllCurrencyExchange()
                .flatMap(this::generateResponse)
                .onErrorResume(throwable -> {
                    log.error("Error fetching exchange rate cause: {} error message: {}", throwable.getCause(), throwable.getMessage());
                    return Mono.error(new ServiceUnavailableException("Failed to retrieve currency exchange transactions"));
                });

    }

    private Mono<CurrencyExchangeDto> generateResponse(CurrencyExchange currencyExchange) {
        return Mono.just(new CurrencyExchangeDto(
                currencyExchange.getOperationId(),
                currencyExchange.getUserId(),
                currencyExchange.getAmount(),
                currencyExchange.getConvertedAmount(),
                currencyExchange.getCurrencyFrom(),
                currencyExchange.getCurrencyTo(),
                currencyExchange.getExchangeRate(),
                currencyExchange.getDateTime()
        ));
    }
}
