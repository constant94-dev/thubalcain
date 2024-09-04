package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.dto.ViewCreateToken;
import com.awl.cert.thubalcain.controller.vo.request.CreateAuthorizeRequest;

public interface JwtsService {
    String createAuthorizeCode(CreateAuthorizeRequest createAuthorizeRequest); // 사용자는 먼저 인가코드를 발급
    String createJWE(ViewCreateToken.Request requestTokenDTO); // 발급된 인가코드로 토큰 생성
}
