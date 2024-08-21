package com.awl.cert.thubalcain.service;

import java.security.NoSuchAlgorithmException;

public interface JwtsService {
    String createAuthorizeCode(String password) throws NoSuchAlgorithmException; // 사용자는 먼저 인가코드를 발급
    String createJWE(String code); // 발급된 인가코드로 토큰 생성
}
