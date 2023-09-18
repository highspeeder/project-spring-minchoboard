package com.cos.blog.controller.api;

import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserApiController {
    // @Autowired
    // private HttpSession session;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    // 회원가입
    @PostMapping(value = "/auth/joinProc")
    public ResponseEntity<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController save 호출됨");

        userService.join(user); 
        
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    // 회원정보 수정
    // RequestBody: Json데이터를 받음
    @PutMapping(value = "/user")
    public ResponseEntity<Integer> update(@RequestBody User user,
            @AuthenticationPrincipal PrincipalDetail principal,
            HttpSession httpSession) {
        userService.update(user);

        // 유저정보 업데이트후 세션에 있는 객체도 바꿔준다.
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    // 시큐리티 안쓴 로그인 방법
    // @PostMapping(value="/auth/login")
    // public ResponseDto<Integer> login(@RequestBody User user) {
    // System.out.println("UserApiController login 호출됨");
    // User loginUser = userService.login(user);

    // if (loginUser != null) {
    // //세션 만들기
    // session.setAttribute("loginUser", loginUser);
    // return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    // }
    // else
    // {
    // return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    // -1);
    // }
    // }

}
