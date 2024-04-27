package com.bankcompany.currencyexchange.infrastructure.persistence.h2;

import com.bankcompany.currencyexchange.application.dao.CurrencyExchangeDAO;
import com.bankcompany.currencyexchange.domain.model.CurrencyExchange;
import com.bankcompany.currencyexchange.infrastructure.persistence.h2.mapper.ExchangeRateMapper;
import com.bankcompany.currencyexchange.infrastructure.persistence.h2.repository.CurrencyExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CurrencyExchangeDAOImpl implements CurrencyExchangeDAO {

    private final CurrencyExchangeRepository repository;
    private final ExchangeRateMapper rateMapper;

    @Override
    public Flux<CurrencyExchange> findAllCurrencyExchange() {

        return repository.findAll()
                .flatMap(currencyExchangeEntity ->
                        Mono.just(rateMapper.toDto(currencyExchangeEntity))
                );
    }

    @Transactional
    @Override
    public Mono<CurrencyExchange> saveCurrencyExchange(CurrencyExchange currencyExchange) {
        return repository.save(rateMapper.toEntity(currencyExchange))
                .flatMap(currencyExchangeEntity -> Mono.just(rateMapper.toDto(currencyExchangeEntity)));
    }

    @Transactional
    @Override
    public Mono<CurrencyExchange> updateCurrencyExchange(CurrencyExchange currencyExchange) {
        return null;
    }

    @Transactional
    @Override
    public Mono<Void> deleteCurrencyExchange(CurrencyExchange currencyExchange) {
        return null;
    }
}
