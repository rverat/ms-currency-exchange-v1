package com.bankcompany.currencyexchange.domain.service.impl;

import com.bankcompany.currencyexchange.application.external_repository.ExchangeRateClientRepository;
import com.bankcompany.currencyexchange.application.repository.CurrencyExchangeDAO;
import com.bankcompany.currencyexchange.domain.model.CurrencyExchange;
import com.bankcompany.currencyexchange.infrastructure.exception.types.ServiceUnavailableException;
import com.bankcompany.currencyexchange.interfaces.dto.CurrencyExchangeDto;
import com.bankcompany.currencyexchange.interfaces.dto.ExchangeRateRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeServiceImplTest {

    @Mock
    private ExchangeRateClientRepository exchangeRateClient;

    @Mock
    private CurrencyExchangeDAO exchangeDAO;

    @InjectMocks
    private CurrencyExchangeServiceImpl service;

    @Test
    void testApplyExchangeRate_Success() {

        var exchangeRate = BigDecimal.valueOf(0.37);
        var dateTime = LocalDateTime.now();
        var requestDto = new ExchangeRateRequestDto(1, BigDecimal.valueOf(100.54),
                "USD", "PEN");
        var currencyExchange = new CurrencyExchange(1, 1,
                BigDecimal.valueOf(100.54), BigDecimal.valueOf(37.40),
                "USD", "PEN", exchangeRate, dateTime);
        var expectedDto = new CurrencyExchangeDto(1, 1,
                BigDecimal.valueOf(100.54), BigDecimal.valueOf(37.40),
                "USD", "PEN", exchangeRate, dateTime);

        when(exchangeRateClient.getExchangeRate("USD", "PEN"))
                .thenReturn(Mono.just(exchangeRate));
        when(exchangeDAO.saveCurrencyExchange(any()))
                .thenReturn(Mono.just(currencyExchange));

        StepVerifier.create(service.applyExchangeRate(requestDto))
                .expectNext(expectedDto)
                .verifyComplete();
    }

    @Test
    void testApplyExchangeRate_ServiceUnavailable() {

        var requestDto = new ExchangeRateRequestDto(1, BigDecimal.valueOf(100.54),
                "USD", "PEN");

        when(exchangeRateClient.getExchangeRate("USD", "PEN"))
                .thenReturn(Mono.error(new ServiceUnavailableException("Service unavailable")));

        StepVerifier.create(service.applyExchangeRate(requestDto))
                .expectError(ServiceUnavailableException.class)
                .verify();
    }

    @Test
    void testGetAllMovementExchangeRate_Success() {

        var dateTime = LocalDateTime.now();
        var currencyExchange = new CurrencyExchange(1, 1,
                BigDecimal.valueOf(100.54), BigDecimal.valueOf(37.40),
                "USD", "PEN", BigDecimal.valueOf(0.37), dateTime);
        var expectedDto = new CurrencyExchangeDto(1, 1,
                BigDecimal.valueOf(100.54), BigDecimal.valueOf(37.40),
                "USD", "PEN", BigDecimal.valueOf(0.37), dateTime);

        when(exchangeDAO.findAllCurrencyExchange())
                .thenReturn(Flux.just(currencyExchange));

        StepVerifier.create(service.getAllMovementExchangeRate())
                .expectNext(expectedDto)
                .verifyComplete();
    }


    @Test
    void testGetAllMovementExchangeRate_ServiceUnavailable() {

        when(exchangeDAO.findAllCurrencyExchange())
                .thenReturn(Flux.error(new RuntimeException("Service unavailable")));

        StepVerifier.create(service.getAllMovementExchangeRate())
                .expectError(ServiceUnavailableException.class)
                .verify();
    }
}
