package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.controller.dto.request.ViewCreateSession;
import com.awl.cert.thubalcain.controller.response.ApiResponse;
import com.awl.cert.thubalcain.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/redis/session")
@RequiredArgsConstructor
public class RedisSessionController {
    private final RedisService redisService;

    @PostMapping("/find")
    public ResponseEntity<ApiResponse<Object>> find(@RequestBody ViewCreateSession.Request req) {
        log.info("request 확인: {}", req);

        Map<Object, Object> response = redisService.findUserSession(req);

        for (Map.Entry<Object, Object> res : response.entrySet()) {
            log.info("key: {} value: {}",res.getKey(),res.getValue());
        }

        return new ResponseEntity<>(ApiResponse.success("요청한 사용자 세션 정보.", response), HttpStatus.OK);
    }
}
