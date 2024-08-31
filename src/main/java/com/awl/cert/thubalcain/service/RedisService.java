package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.api.dto.RequestAuthorizeDTO;
import com.awl.cert.thubalcain.controller.api.dto.RequestSessionDTO;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface RedisService {
    void addAuthorizeCodeSession(String response, RequestAuthorizeDTO requestAuthorizeDTO, HttpSession httpSession);

    Map<Object, Object> findUserSession(RequestSessionDTO.Request req);
}
