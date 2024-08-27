package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.controller.api.dto.RequestAuthorizeDTO;
import com.awl.cert.thubalcain.service.JwtsService;
import com.awl.cert.thubalcain.service.dto.RequestTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtsService jwtsService;

    @PostMapping("/create/code")
    public ResponseEntity<String> createAuthorizeCode(@Valid @RequestBody RequestAuthorizeDTO requestAuthorizeDTO) {
        String authorizeCode = jwtsService.createAuthorizeCode(requestAuthorizeDTO);
        return ResponseEntity.ok(authorizeCode);
    }

    @PostMapping("/create/jwe")
    public ResponseEntity<String> createJWE(@Valid @RequestBody RequestTokenDTO.Request requestTokenDTO) {
        String token = jwtsService.createJWE(requestTokenDTO);
        return ResponseEntity.ok(token);
    }
}
