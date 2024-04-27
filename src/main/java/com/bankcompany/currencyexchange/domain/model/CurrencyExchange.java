package com.bankcompany.currencyexchange.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchange {
    private int operationId;
    private int userId;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal exchangeRate;
    private LocalDateTime dateTime;
}
