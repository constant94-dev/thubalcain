package com.awl.cert.thubalcain.controller.api.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestAuthorizeDTO(@NotBlank String password) {}
