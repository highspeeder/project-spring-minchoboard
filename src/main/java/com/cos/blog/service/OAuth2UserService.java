package com.cos.blog.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoUserInfo;
import com.cos.blog.model.OAuth2UserInfo;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo userInfo = null;

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (registrationId.equals("kakao")) {
            userInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String id = userInfo.getProviderId();
        String email = userInfo.getEmail();
        String username = registrationId + "_" + id; //중복이 발생하지 않도록 provider와 providerId를 조합
        RoleType role = RoleType.USER; //일반 유저

        //기존에 가입한 유저가 없다면, DB에 저장하는 로직
        Optional<User> findMember = userRepository.findByUsername(username);
        User user=null;

        if (findMember.isEmpty()) { //찾지 못했다면
            user = User.builder()
                    .username(username)
                    .email(email)
                    .password(encoder.encode("password"))
                    .role(role)
                    .isOAuthUser(true)
                    .build();
                    
            userRepository.save(user);
        }
        else{
            user=findMember.get();
        }

        return new PrincipalDetail(user, oAuth2User.getAttributes());
    }
}