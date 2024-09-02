package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.dto.request.ViewCreateToken;
import com.awl.cert.thubalcain.controller.vo.request.CreateAuthorizeRequest;
import com.awl.cert.thubalcain.service.impl.JwtsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class JwtsServiceTest {
    private JwtsService jwtsService;

    @BeforeEach
    void setUp() {
        jwtsService = new JwtsServiceImpl();
    }

    private static Stream<ViewCreateToken.Request> provideTokenDTO() {
        String email = "test@gmail.com";
        String name = "testName0827";
        String aud = "testAud0827";

        return Stream.of(
                ViewCreateToken.Request.builder()
                        .email(email)
                        .name(name)
                        .aud(aud)
                        .build()
        );
    }

    @DisplayName("인가 코드 발급 확인")
    @Test
    void createAuthorizeCode() {
        String result = jwtsService.createAuthorizeCode(new CreateAuthorizeRequest("test@gmail.com","123456"));

        /* Base64 인코딩 원리
        * Base64는 6비트 단위로 데이터를 인코딩하여 ASCII 문자열로 표현
        * 이 과정에서 패딩 문자(=)가 추가
        * createAuthorizeCode() 내부에서 동작하는 SHA-256 해시 함수는 항상 32바이트(256비트) 길이의 해시 값을 생성
        * 아래와 같은 계산이 이루어짐
        * 256비트 / 6비트 = 42.666… → 43 (올림) + 패딩 1 = 44바이트
        * */
        double encodedSize = (double) 256 / 6;
        int expectedSize = (int) Math.ceil(encodedSize) + 1;

        assertThat(result).hasSize(expectedSize);
    }

    @DisplayName("JWE 토큰 발급 확인")
    @ParameterizedTest
    @MethodSource("provideTokenDTO")
    void createJWE(ViewCreateToken.Request tokenDTO) {
        String authCode = jwtsService.createAuthorizeCode(new CreateAuthorizeRequest("test@gmail.com","123456"));
        tokenDTO.updateAuthCode(authCode);
        String jwe = jwtsService.createJWE(tokenDTO);

        String[] parts = jwe.split("\\.");

        assertThat(parts).hasSize(5);
    }
}