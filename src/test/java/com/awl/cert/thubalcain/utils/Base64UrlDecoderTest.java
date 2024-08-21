package com.awl.cert.thubalcain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Method;

class Base64UrlDecoderTest {

  @DisplayName("base64 decode 성공 테스트")
  @ParameterizedTest
  @ValueSource(strings = {"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"})
  void decodeBase64UrlToSuccess(String base64Url) {
    final String expected = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    final String result = Base64UrlDecoderUtils.decodeBase64Url(base64Url);

    Assertions.assertEquals(expected, result);
  }

  @ParameterizedTest
  @CsvSource(value = {
      "SGVsbG8tV29ybGQ, SGVsbG8tV29ybGQ=",
      "SGVsbG8tV29ybGQSGVsbG8tV29ybGQ=, SGVsbG8tV29ybGQSGVsbG8tV29ybGQ==",
      "SGVsbG8tV29ybGQW, SGVsbG8tV29ybGQW"
  })
  @DisplayName("base64 decode 패딩 추가 테스트")
  void addPaddingDecodeBase64Url(String base64Url, String expectedBase64Url) throws Exception {
      // given
    Method method = Base64UrlDecoderUtils.class.getDeclaredMethod("base64UrlToBase64", String.class);
    method.setAccessible(true);

      // when
    final String actualBase64 = (String) method.invoke(null, base64Url);

      // then
    Assertions.assertEquals(expectedBase64Url, actualBase64);
  }
}