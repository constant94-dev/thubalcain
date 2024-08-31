package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.api.dto.RequestAuthorizeDTO;
import com.awl.cert.thubalcain.controller.api.dto.RequestTokenDTO;

public interface JwtsService {
    String createAuthorizeCode(RequestAuthorizeDTO requestAuthorizeDTO); // 사용자는 먼저 인가코드를 발급
    String createJWE(RequestTokenDTO.Request requestTokenDTO); // 발급된 인가코드로 토큰 생성
}
