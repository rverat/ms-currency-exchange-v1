package com.bankcompany.currencyexchange.infrastructure.persistence.h2.mapper;


import com.bankcompany.currencyexchange.domain.model.CurrencyExchange;
import com.bankcompany.currencyexchange.infrastructure.persistence.h2.entity.CurrencyExchangeEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

    CurrencyExchange toDto(CurrencyExchangeEntity exchangeRate);

    CurrencyExchangeEntity toEntity(CurrencyExchange currencyExchange);
}
