package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.service.JwtsService;
import com.awl.cert.thubalcain.service.dto.RequestTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtsService jwtsService;

    @PostMapping("/create/code")
    public String createAuthorizeCode(@RequestBody String password) {
        return jwtsService.createAuthorizeCode(password);
    }

    @PostMapping("/create/jwe")
    public String createJWE(@RequestBody RequestTokenDTO requestTokenDTO) {
        return jwtsService.createJWE(requestTokenDTO);
    }
}
