package com.awl.cert.thubalcain.controller.dto;

import lombok.Getter;
import lombok.ToString;

public class ViewCreateSession {

    @Getter
    @ToString
    public static class Request {
        private String key;
        private String hashKey;
    }
}
