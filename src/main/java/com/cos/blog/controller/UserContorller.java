package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소가 /이면 index.html 허용
//static이하에 있는 /js, /css, /image허용
@Controller
public class UserContorller {

    @GetMapping(value = "/user/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping(value = "/user/loginForm")
    public String loginForm(@RequestParam(required = false) boolean hasMessage, @RequestParam(required = false) String message, Model model) {
        model.addAttribute("hasMessage", hasMessage);
        model.addAttribute("message", message);

        return "user/loginForm";
    }

    @GetMapping(value = "/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
