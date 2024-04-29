package com.bankcompany.currencyexchange.interfaces.web;

import com.bankcompany.currencyexchange.domain.service.CurrencyExchangeService;
import com.bankcompany.currencyexchange.interfaces.dto.CurrencyExchangeDto;
import com.bankcompany.currencyexchange.interfaces.dto.ExchangeRateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequestMapping("/api/v1/currency-exchange")
@RequiredArgsConstructor
@RestController
@Slf4j
public class CurrencyExchangeController {

    private final CurrencyExchangeService exchangeService;

    @PostMapping("/apply")
    public Mono<CurrencyExchangeDto> convertCurrency(@RequestBody ExchangeRateRequestDto requestDto) {

        return exchangeService.applyExchangeRate(requestDto);
    }

    @GetMapping("/getAll")
    public Flux<CurrencyExchangeDto> getAllTransaction() {

        return exchangeService.getAllMovementExchangeRate();
    }
}

