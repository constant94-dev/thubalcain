package com.awl.cert.thubalcain.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
