package com.awl.cert.thubalcain.utils;

import com.awl.cert.thubalcain.controller.vo.request.CreateAuthorizeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterJsonUtilsTest {

    @DisplayName("JSON 파일을 읽어 객체로 변환하는 기능 확인")
    @Test
    void readFileToMapper() {
        CreateAuthorizeRequest authorizeDTO = ConverterJsonUtils.readFileToMapper("request/create-authorize-code-request.json", CreateAuthorizeRequest.class);

        assertThat(authorizeDTO.password()).isEqualTo("123456");
    }

    @DisplayName("객체를 JSON 문자열로 변환하는 기능 확인")
    @ParameterizedTest
    @ValueSource(strings = {"123456"})
    void writeToJsonString(String password) throws JsonProcessingException {
        CreateAuthorizeRequest authorizeDTO = new CreateAuthorizeRequest("test@gmail.com",password);

        String actual = ConverterJsonUtils.writeToJsonString(authorizeDTO);
        String expected = "{\"password\":\"123456\"}";

        assertThat(actual).isEqualTo(expected);
    }
}