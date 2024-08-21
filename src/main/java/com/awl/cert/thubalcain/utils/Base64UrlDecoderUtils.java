package com.awl.cert.thubalcain.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class Base64UrlDecoderUtils {
    private static String base64UrlToBase64(String base64Url) {
        String base64 = base64Url
                .replace("-", "+")
                .replace("_", "/");

        // 패딩이 누락 되었을 경우 추가
        int paddingLength = (4 - (base64.length() % 4)) % 4;
        base64 += "=".repeat(paddingLength);

        log.info("패딩 추가 로직 후 결과: {}", base64);
        return base64;
    }

    public static String decodeBase64Url(String base64Url) {
        String base64 = base64UrlToBase64(base64Url);

        byte[] decoded = Base64.getDecoder().decode(base64);

        return new String(decoded, StandardCharsets.UTF_8);
    }

    protected Base64UrlDecoderUtils() {}
}
