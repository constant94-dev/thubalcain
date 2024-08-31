package com.awl.cert.thubalcain.controller.api.dto;

import lombok.Getter;
import lombok.ToString;

public class RequestSessionDTO {

    @Getter
    @ToString
    public static class Request {
        private String key;
        private String hashKey;
    }
}
