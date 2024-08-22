package com.awl.cert.thubalcain.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private String email;
    private String name;
    private String tel;
}
