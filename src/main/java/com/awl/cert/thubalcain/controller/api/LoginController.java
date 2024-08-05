package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.service.GoogleService;
import com.awl.cert.thubalcain.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final KakaoService kakaoService;
    private final GoogleService googleService;

    @RequestMapping("/kakao-login")
    public RedirectView goKakaoOAuth() {
        return kakaoService.goKakaoOAuth();
    }

    @RequestMapping("/login-callback")
    public RedirectView loginCallback(@RequestParam("code") String code) {
        return kakaoService.loginCallback(code);
    }

    @RequestMapping("/google-login")
    public RedirectView goGoogleOAuth() {
        return googleService.goGoogleOAuth();
    }

    @RequestMapping("/google-login-callback")
    public RedirectView loginCallback(){
        return googleService.loginCallback();
    }
}
