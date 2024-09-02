package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.dto.request.ViewCreateUser;

public interface UserService {
    ViewCreateUser.Response createUser(ViewCreateUser.Request request);
}
