package com.awl.cert.thubalcain.service.impl;

import com.awl.cert.thubalcain.controller.dto.request.ViewCreateSession;
import com.awl.cert.thubalcain.controller.vo.request.CreateAuthorizeRequest;
import com.awl.cert.thubalcain.service.RedisService;
import com.awl.cert.thubalcain.utils.DateTimeUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private static final String SESSION_PREFIX = "spring_session:sessions:";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void addAuthorizeCodeSession(String response, CreateAuthorizeRequest createAuthorizeRequest, HttpSession httpSession) {
        Map<String, Object> mapToSession = new HashMap<>();

        LocalDateTime currentTime = DateTimeUtils.generateIssuedAt();
        String convertedTime = DateTimeUtils.convertLocalDateTimeToString(currentTime);
        mapToSession.put("creationTime", convertedTime);
        mapToSession.put("lastAccessedTime", convertedTime);
        mapToSession.put("maxInactiveInterval", convertedTime);
        mapToSession.put("authCode", response);

        redisTemplate.opsForHash().putAll(SESSION_PREFIX + httpSession.getId(), mapToSession);
    }

    @Override
    public Map<Object, Object> findUserSession(ViewCreateSession.Request req) {
        return redisTemplate.opsForHash().entries(req.getKey());
    }
}
