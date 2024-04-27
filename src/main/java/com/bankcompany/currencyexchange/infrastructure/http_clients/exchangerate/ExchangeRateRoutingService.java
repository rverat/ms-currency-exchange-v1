package com.bankcompany.currencyexchange.infrastructure.http_clients.exchangerate;

import com.bankcompany.currencyexchange.application.client.ExchangeRateClient;
import com.bankcompany.currencyexchange.infrastructure.exception.types.NotFoundException;
import com.bankcompany.currencyexchange.infrastructure.exception.types.ServiceUnavailableException;
import com.bankcompany.currencyexchange.infrastructure.http_clients.exchangerate.model.ExchangeRateApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Service
public class ExchangeRateRoutingService implements ExchangeRateClient {


    private final WebClient.Builder webClientBuilder;

    public ExchangeRateRoutingService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<BigDecimal> getExchangeRate(String originalCurrency, String targetCurrency) {
        WebClient webClient = webClientBuilder.baseUrl("https://open.er-api.com/v6").build();
        return webClient.get()
                .uri("/latest/{baseCode}", originalCurrency)
                .retrieve()
                .bodyToMono(ExchangeRateApiResponse.class)
                .switchIfEmpty(Mono.error(new NotFoundException("Not found exchange rates")))
                .map(exchangeRateApiResponse -> BigDecimal.valueOf(exchangeRateApiResponse
                        .getRates()
                        .get(targetCurrency))
                ).onErrorResume(throwable -> {
                    log.error("Error fetching exchange rate cause: {} error message: {}", throwable.getCause(), throwable.getMessage());

                    if (throwable instanceof NullPointerException) {
                        return Mono.error(new ServiceUnavailableException("Failed to retrieve exchange rate"));
                    }
                    return Mono.error(throwable);
                });
    }
}
