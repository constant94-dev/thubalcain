package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.dto.request.ViewCreateSession;
import com.awl.cert.thubalcain.controller.vo.request.CreateAuthorizeRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface RedisService {
    void addAuthorizeCodeSession(String response, CreateAuthorizeRequest createAuthorizeRequest, HttpSession httpSession);

    Map<Object, Object> findUserSession(ViewCreateSession.Request req);
}
