package com.bankcompany.currencyexchange.interfaces.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Valid
public record CurrencyExchangeDto(@NotNull
                                  @Min(value = 1, message = "Operation ID must be a positive integer")
                                  int operationId,

                                  @NotNull
                                  @Min(value = 1, message = "User ID must be a positive integer")
                                  int userId,

                                  @NotNull
                                  @DecimalMin(value = "0.01", message = "Amount must be a positive decimal number")
                                  BigDecimal amount,

                                  @NotNull
                                  @DecimalMin(value = "0.01", message = "Converted amount must be a non-negative decimal number")
                                  BigDecimal convertedAmount,

                                  @NotBlank(message = "Currency from code cannot be empty")
                                  @Size(max = 3, message = "Currency from code must be 3 characters long")
                                  String currencyFrom,

                                  @NotBlank(message = "Currency to code cannot be empty")
                                  @Size(max = 3, message = "Currency to code must be 3 characters long")
                                  String currencyTo,

                                  @NotNull
                                  @DecimalMin(value = "0.00", message = "Exchange rate must be a positive decimal number")
                                  BigDecimal exchangeRate,

                                  @NotNull(message = "dateTime cannot be null")
                                  LocalDateTime dateTime
) {
}
