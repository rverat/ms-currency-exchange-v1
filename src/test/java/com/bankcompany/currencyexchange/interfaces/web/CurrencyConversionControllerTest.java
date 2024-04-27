package com.bankcompany.currencyexchange.interfaces.web;

import com.bankcompany.currencyexchange.domain.service.CurrencyExchangeService;
import com.bankcompany.currencyexchange.interfaces.dto.CurrencyExchangeDto;
import com.bankcompany.currencyexchange.interfaces.dto.ExchangeRateRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionControllerTest {

    @Mock
    private CurrencyExchangeService exchangeService;
    @InjectMocks
    private CurrencyConversionController controller;

    @Test
    void testConvertCurrency() {

        var requestDto = new ExchangeRateRequestDto(1, BigDecimal.valueOf(100.54), "USD", "PEN");
        var expectedResult = new CurrencyExchangeDto(1, 1, BigDecimal.valueOf(100.54), BigDecimal.valueOf(376.91983516),
                "USD", "PEN", BigDecimal.valueOf(3.748954), LocalDateTime.now());

        when(exchangeService.applyExchangeRate(any(ExchangeRateRequestDto.class)))
                .thenReturn(Mono.just(expectedResult));

        WebTestClient
                .bindToController(controller)
                .build()
                .post()
                .uri("/currency-conversion/applyConvertion")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CurrencyExchangeDto.class)
                .isEqualTo(expectedResult);
    }

    @Test
    void testGetAllTransaction() {
        var expectedResult = Arrays.asList(
                new CurrencyExchangeDto(1, 1, BigDecimal.valueOf(100.54), BigDecimal.valueOf(376.91983516),
                        "USD", "PEN", BigDecimal.valueOf(3.748954), LocalDateTime.now()),
                new CurrencyExchangeDto(2, 1, BigDecimal.valueOf(100.54), BigDecimal.valueOf(376.91983516),
                        "USD", "PEN", BigDecimal.valueOf(3.748954), LocalDateTime.now())
        );

        when(exchangeService.getAllMovementExchangeRate())
                .thenReturn(Flux.fromIterable(expectedResult));

        WebTestClient
                .bindToController(controller)
                .build()
                .get()
                .uri("/currency-conversion/convertions")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CurrencyExchangeDto.class)
                .isEqualTo(expectedResult);
    }

}
