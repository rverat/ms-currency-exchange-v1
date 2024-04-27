package com.bankcompany.currencyexchange.infrastructure.persistence.h2.h2;

import com.bankcompany.currencyexchange.domain.model.CurrencyExchange;
import com.bankcompany.currencyexchange.infrastructure.persistence.h2.CurrencyExchangeDAOImpl;
import com.bankcompany.currencyexchange.infrastructure.persistence.h2.entity.CurrencyExchangeEntity;
import com.bankcompany.currencyexchange.infrastructure.persistence.h2.mapper.ExchangeRateMapper;
import com.bankcompany.currencyexchange.infrastructure.persistence.h2.repository.CurrencyExchangeRepository;
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
import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeDAOImplTest {
    @Mock
    private CurrencyExchangeRepository repository;

    @Mock
    private ExchangeRateMapper rateMapper;

    @InjectMocks
    private CurrencyExchangeDAOImpl dao;

    @Test
    void testFindAllCurrencyExchange() {

        var entity1 = new CurrencyExchangeEntity(1, 1, BigDecimal.valueOf(100), BigDecimal.valueOf(30),
                "USD", "PEN", BigDecimal.valueOf(0.32), LocalDateTime.now());
        var entity2 = new CurrencyExchangeEntity(2, 1, BigDecimal.valueOf(100), BigDecimal.valueOf(30),
                "USD", "PEN", BigDecimal.valueOf(0.32), LocalDateTime.now());
        var currencyExchange1 = new CurrencyExchange(1, 1, BigDecimal.valueOf(100), BigDecimal.valueOf(30),
                "USD", "PEN", BigDecimal.valueOf(0.32), LocalDateTime.now());
        var currencyExchange2 = new CurrencyExchange(2, 1, BigDecimal.valueOf(100), BigDecimal.valueOf(30),
                "USD", "PEN", BigDecimal.valueOf(0.32), LocalDateTime.now());
        var entities = Arrays.asList(entity1, entity2);
        var expected = Arrays.asList(currencyExchange1, currencyExchange2);

        when(repository.findAll()).thenReturn(Flux.fromIterable(entities));
        when(rateMapper.toDto(entity1)).thenReturn(currencyExchange1);
        when(rateMapper.toDto(entity2)).thenReturn(currencyExchange2);

        StepVerifier.create(dao.findAllCurrencyExchange())
                .expectNextSequence(expected)
                .verifyComplete();
    }

    @Test
    void testSaveCurrencyExchange() {

        var input = new CurrencyExchange(1, 1, BigDecimal.valueOf(100), BigDecimal.valueOf(30), "USD", "PEN", BigDecimal.valueOf(0.32), LocalDateTime.now());
        var entity = new CurrencyExchangeEntity(1, 1, BigDecimal.valueOf(100), BigDecimal.valueOf(30), "USD", "PEN", BigDecimal.valueOf(0.32), LocalDateTime.now());

        when(rateMapper.toEntity(input)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(rateMapper.toDto(entity)).thenReturn(input);

        StepVerifier.create(dao.saveCurrencyExchange(input))
                .expectNext(input)
                .verifyComplete();
    }

}
