package com.bankcompany.currencyexchange.interfaces.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Valid
public record ExchangeRateRequestDto(@NotNull
                                     @Min(value = 1, message = "User ID must be a positive integer")
                                     int userId,
                                     @NotNull
                                     @DecimalMin(value = "0.01", message = "Amount must be a positive decimal number")
                                     BigDecimal amount,
                                     @NotBlank(message = "Currency from code cannot be empty")
                                     @Size(max = 3, message = "Currency from code must be 3 characters long")
                                     String currencyFrom,

                                     @NotBlank(message = "Currency to code cannot be empty")
                                     @Size(max = 3, message = "Currency to code must be 3 characters long")
                                     String currencyTo
) {

}
