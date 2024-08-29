package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.controller.api.dto.RequestAuthorizeDTO;
import com.awl.cert.thubalcain.controller.response.ApiResponse;
import com.awl.cert.thubalcain.service.JwtsService;
import com.awl.cert.thubalcain.service.dto.RequestTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.awl.cert.thubalcain.common.ErrorCode.INVALID_KEY_SPEC;
import static com.awl.cert.thubalcain.common.ErrorCode.NO_SUCH_ALGORITHM;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtsService jwtsService;

    @PostMapping("/create/code")
    public ResponseEntity<ApiResponse<?>> createAuthorizeCode(@Valid @RequestBody RequestAuthorizeDTO requestAuthorizeDTO) {
        String response = jwtsService.createAuthorizeCode(requestAuthorizeDTO);
        if (NO_SUCH_ALGORITHM.getReason().equals(response) || INVALID_KEY_SPEC.getReason().equals(response)) {
            return new ResponseEntity<>(ApiResponse.error(response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ApiResponse.success("인가 코드 발급 성공.", response), HttpStatus.OK);
    }

    @PostMapping("/create/jwe")
    public ResponseEntity<ApiResponse<?>> createJWE(@Valid @RequestBody RequestTokenDTO.Request requestTokenDTO) {
        String response = jwtsService.createJWE(requestTokenDTO);
        if (response.isBlank()) {
            return new ResponseEntity<>(ApiResponse.error(response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ApiResponse.success("인증 토큰 발급 성공.", response), HttpStatus.OK);
    }
}
