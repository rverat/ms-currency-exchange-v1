package com.bankcompany.currencyexchange.infrastructure.persistence.h2.repository;


import com.bankcompany.currencyexchange.infrastructure.persistence.h2.entity.CurrencyExchangeEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends R2dbcRepository<CurrencyExchangeEntity, Integer> {

}


