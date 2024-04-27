package com.bankcompany.currencyexchange.infrastructure.persistence.h2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeEntity {
    @Id
    private int operationId;
    private int userId;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal exchangeRate;
    private LocalDateTime dateTime;
}
