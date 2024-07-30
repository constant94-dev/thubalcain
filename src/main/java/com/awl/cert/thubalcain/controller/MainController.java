package com.awl.cert.thubalcain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(){
        return "html/index.html";
    }

    @GetMapping("/my-data")
    public String myData(){
        return "html/my-data.html";
    }

    @GetMapping("/sign-up")
    public String signUp(){
        return "html/sign-up.html";
    }

    @GetMapping("/sign-in")
    public String signIn(){
        return "html/sign-in.html";
    }

    @GetMapping("/about")
    public String about(){
        return "html/about.html";
    }
}
