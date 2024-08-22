package com.awl.cert.thubalcain.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestTokenDTO {
    private String email;
    private String name;
    private String aud;
}
