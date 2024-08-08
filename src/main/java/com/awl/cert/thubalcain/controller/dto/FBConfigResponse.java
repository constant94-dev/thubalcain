package com.awl.cert.thubalcain.controller.dto;

import com.awl.cert.thubalcain.auth.FirebaseConfig;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

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
