package com.cos.blog.controller.api;

import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

//import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserApiController {
    // @Autowired
    // private HttpSession session;

    @Autowired
    private UserService userService;

    //회원가입
    @PostMapping(value="/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController save 호출됨");

        userService.join(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //시큐리티 안쓴 로그인 방법
    // @PostMapping(value="/auth/login")
    // public ResponseDto<Integer> login(@RequestBody User user) {
    //     System.out.println("UserApiController login 호출됨");
    //     User loginUser = userService.login(user);

    //     if (loginUser != null) {
    //         //세션 만들기
    //         session.setAttribute("loginUser", loginUser);
    //         return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    //     }
    //     else
    //     {
    //         return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1);
    //     }
    // }
    
}
