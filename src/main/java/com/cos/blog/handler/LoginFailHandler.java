package com.cos.blog.handler;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // String message = getMessage(exception);
        
        String message = "로그인에 실패했습니다.";
        String encodeMessage = URLEncoder.encode(message, "UTF-8");
        String redirectUrl = "/user/loginForm?hasMessage=true&message=" + encodeMessage;
        response.sendRedirect(redirectUrl);
        // setDefaultFailureUrl(redirectUrl);
        // super.onAuthenticationFailure(request, response, exception);
    }

    // private static String getMessage(AuthenticationException exception) throws UnsupportedEncodingException {
    //     String message = exception.getMessage();
    //     String encodeMessage = URLEncoder.encode(message, "UTF-8");
    //     return encodeMessage;
    // }
}
