package com.awl.cert.thubalcain.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestTokenDTO {
    private String email;
    private String name;
    private String aud;
    private String authCode;

    public String updateAuthCode(String authCode) {
        this.authCode = authCode;
        return authCode;
    }
}
