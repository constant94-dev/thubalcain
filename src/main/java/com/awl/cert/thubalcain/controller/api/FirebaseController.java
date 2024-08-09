package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.auth.FirebaseConfig;
import com.awl.cert.thubalcain.controller.dto.FBConfigResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/firebase")
public class FirebaseController {
    private final FirebaseConfig firebaseConfig;

    @GetMapping("/config")
    public FBConfigResponse getConfig() {
        return FBConfigResponse.builder()
                .apiKey(firebaseConfig.getApiKey())
                .authDomain(firebaseConfig.getAuthDomain())
                .projectId(firebaseConfig.getProjectId())
                .storageBucket(firebaseConfig.getStorageBucket())
                .messagingSenderId(firebaseConfig.getMessagingSenderId())
                .appId(firebaseConfig.getAppId())
                .measurementId(firebaseConfig.getMeasurementId())
                .build();
    }

    @GetMapping("/phone")
    public void authPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        log.info("전화번호 컨트롤러 호출! {}", phoneNumber);

    }
}
