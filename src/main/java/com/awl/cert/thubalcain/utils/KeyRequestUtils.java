package com.awl.cert.thubalcain.utils;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.Password;

import java.security.Provider;
import java.security.SecureRandom;

public class KeyRequestUtils {

    public static KeyRequest<Password> createKeyRequest(final Password password) {
        return new KeyRequest<Password>() {
            @Override
            public AeadAlgorithm getEncryptionAlgorithm() {
                return Jwts.ENC.A256CBC_HS512;
            }

            @Override
            public JweHeader getHeader() {
                return null;
            }

            @Override
            public Provider getProvider() {
                return null;
            }

            @Override
            public SecureRandom getSecureRandom() {
                return null;
            }

            @Override
            public Password getPayload() {
                return null;
            }
        };
    }

}
