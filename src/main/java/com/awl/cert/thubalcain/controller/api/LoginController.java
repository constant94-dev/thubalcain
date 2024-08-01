package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final  KakaoService kakaoService;

    @RequestMapping("/kakao-login")
    public RedirectView goKakaoOAuth() {
        return kakaoService.goKakaoOAuth();
    }

    @RequestMapping("/login-callback")
    public RedirectView loginCallback(@RequestParam("code") String code) {
        return kakaoService.loginCallback(code);
    }
}
