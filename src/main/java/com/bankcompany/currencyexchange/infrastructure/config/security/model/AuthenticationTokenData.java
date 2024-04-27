package com.bankcompany.currencyexchange.infrastructure.config.security.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationTokenData {

    private String userAudience;
    private String userName;
    private String token;
}
