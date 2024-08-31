package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.api.dto.RequestAuthorizeDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCRUDTestService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object find(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    public void updateValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void updateSession() {
        redisTemplate.opsForHash().put("","","");
    }

    public void addAuthorizeCodeSession(String response, RequestAuthorizeDTO requestAuthorizeDTO, HttpSession httpSession) {

        httpSession.setAttribute(requestAuthorizeDTO.email(), response);
    }
}
