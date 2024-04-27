package com.bankcompany.currencyexchange.infrastructure.config.security.util;

import com.auth0.jwt.algorithms.Algorithm;

import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JwtAlgorithmManager {

    private final String rsaPublicKey;
    private final String rsaPrivateKey;

    public JwtAlgorithmManager(String rsaPublicKey, String rsaPrivateKey) {
        this.rsaPublicKey = rsaPublicKey;
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public Algorithm getTokenAlgorithm() {
        try {
            var rsaUtil = new RsaUtil();
            RSAPublicKey publicKey = null;
            if (this.rsaPublicKey != null) {
                publicKey = (RSAPublicKey) rsaUtil.getPublicKey(this.rsaPublicKey);
            }
            RSAPrivateKey privateKey = null;
            if (this.rsaPrivateKey != null) {
                privateKey = (RSAPrivateKey) rsaUtil.getPrivateKey(this.rsaPrivateKey);
            }
            return Algorithm.RSA512(publicKey, privateKey);
        } catch (GeneralSecurityException ex) {
            throw new RuntimeException(ex);
        }
    }
}
