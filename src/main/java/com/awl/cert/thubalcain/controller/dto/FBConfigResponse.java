package com.awl.cert.thubalcain.controller.dto;

import lombok.*;

@Getter
@Builder
public class FBConfigResponse {
    private String apiKey;
    private String authDomain;
    private String projectId;
    private String storageBucket;
    private String messagingSenderId;
    private String appId;
    private String measurementId;
}
