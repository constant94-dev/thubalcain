package com.awl.cert.thubalcain.service.impl;

import com.awl.cert.thubalcain.service.JwtsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JwtsServiceImplTest {
    @Mock
    private JwtsService jwtsService;

    @DisplayName("jwe 생성을 위한 SecretKey 생성 확인")
    @ParameterizedTest
    @ValueSource(strings = {"123456789"})
    void createAuthorizeCode(String password) throws NoSuchAlgorithmException {
        Mockito.when(jwtsService.createAuthorizeCode(password)).thenReturn("authorizecode");

        String result = jwtsService.createAuthorizeCode(password);
        String expected = "authorizecode";

        assertThat(result).isEqualTo(expected);
    }
}