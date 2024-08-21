package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.service.dto.UserDTO;

import java.security.NoSuchAlgorithmException;

public interface JwtsService {
    String createAuthorizeCode(String password) throws NoSuchAlgorithmException; // 사용자는 먼저 인가코드를 발급
    String createJWE(String code, UserDTO userDTO); // 발급된 인가코드로 토큰 생성
}
