package com.bankcompany.currencyexchange.interfaces.web;

import com.bankcompany.currencyexchange.domain.service.CurrencyExchangeService;
import com.bankcompany.currencyexchange.interfaces.dto.CurrencyExchangeDto;
import com.bankcompany.currencyexchange.interfaces.dto.ExchangeRateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/currency-conversion")
@RequiredArgsConstructor
@Slf4j
public class CurrencyConversionController {

    private final CurrencyExchangeService exchangeService;

    @PostMapping("applyConvertion")
    public Mono<CurrencyExchangeDto> convertCurrency(@RequestBody ExchangeRateRequestDto requestDto) {

        return exchangeService.applyExchangeRate(requestDto);
    }

    @GetMapping("/convertions")
    public Flux<CurrencyExchangeDto> getAllTransaction() {

        return exchangeService.getAllMovementExchangeRate();
    }
}

