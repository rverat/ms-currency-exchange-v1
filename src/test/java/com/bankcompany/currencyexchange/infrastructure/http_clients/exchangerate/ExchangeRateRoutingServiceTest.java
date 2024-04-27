package com.bankcompany.currencyexchange.infrastructure.http_clients.exchangerate;

import com.bankcompany.currencyexchange.infrastructure.exception.types.NotFoundException;
import com.bankcompany.currencyexchange.infrastructure.exception.types.ServiceUnavailableException;
import com.bankcompany.currencyexchange.infrastructure.http_clients.exchangerate.model.ExchangeRateApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRateRoutingServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private ExchangeRateRoutingService service;

    @Test
    void testGetExchangeRate_Success() {

        var apiResponse = new ExchangeRateApiResponse();
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.82); // Rate for EUR
        apiResponse.setRates(rates);
        var requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        var responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.baseUrl(any(String.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(Mockito.mock(WebClient.class));
        when(webClientBuilder.build().get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/latest/{baseCode}", "USD")).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateApiResponse.class)).thenReturn(Mono.just(apiResponse));

        StepVerifier.create(service.getExchangeRate("USD", "EUR"))
                .expectNext(BigDecimal.valueOf(0.82)) // Expecting the rate for EUR
                .verifyComplete();
    }

    @Test
    void testGetExchangeRate_ServiceUnavailable() {

        var requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        var responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.baseUrl(any(String.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(Mockito.mock(WebClient.class));
        when(webClientBuilder.build().get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/latest/{baseCode}", "USD")).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateApiResponse.class)).thenReturn(Mono.empty()); // Simulating empty response

        StepVerifier.create(service.getExchangeRate("USD", "EUR"))
                .expectError(NotFoundException.class) // Expecting NotFoundException
                .verify();
    }

    @Test
    void testGetExchangeRate_InternalServerError() {
        var requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        var responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.baseUrl(any(String.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(Mockito.mock(WebClient.class));
        when(webClientBuilder.build().get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/latest/{baseCode}", "USD")).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateApiResponse.class)).thenReturn(Mono.error(new NullPointerException())); // Simulating InternalServerError

        StepVerifier.create(service.getExchangeRate("USD", "EUR"))
                .expectError(ServiceUnavailableException.class) // Expecting ServiceUnavailableException
                .verify();
    }


}
