package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cos.blog.handler.LoginFailHandler;
import com.cos.blog.service.OAuth2UserService;

@Configuration // IoC
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
// @RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2UserService oAuth2UserService;
 
    public SecurityConfig(OAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
    }
    // 비밀번호 해시화
    // 시큐리티가 로그인할때 자동으로 이 encoder를 사용하여
    // 같은 해시로 암호화해서 DB에 있는 해시랑 비교할 수 있음.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // .csrf(CsrfConfigurer::disable)
                // 어떤 요청이 들어오면
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // /auth라는 uri로 들어온 경우 통과(permitAll:어떠한 보안 요구 없이 요청을 허용)
                        .requestMatchers("/", "/auth/**", "/user/**", "/js/**", "/css/**", "/image/**", "/favicon.ico",
                                "/oauth2/**")
                        .permitAll()
                        // 위에 요청이 아닌 다른 모든 요청은 인증이 되어야 통과할 수 있다.
                        .anyRequest().authenticated())
                .oauth2Login(o -> o
                    .loginPage("/user/loginForm")
                    .defaultSuccessUrl("/")
                    .userInfoEndpoint(userInfo -> userInfo
			            .userService(this.oAuth2UserService)
                    )
                )
                .formLogin(formlogin -> formlogin
                        // 인증이 되지 않은 모든 요청은 자동으로 로그인 페이지로 간다.
                        // 로그인 페이지 지정
                        .loginPage("/user/loginForm")
                        // 스프링 시큐리티가 해당 url로 요청오는 로그인을 가로채서 대신 로그인 해준다.
                        .loginProcessingUrl("/auth/loginProc")
                        // 정상적으로 로그인이 되면 해당 url로 이동
                        .defaultSuccessUrl("/")
                        .failureHandler(new LoginFailHandler()))
                // csrf토큰을 사용하지 않으면 기본적으로 /logout url으로 로그아웃이 되지만
                // csrf토큰을 사용한다면 아래와 같이 설정을 해준다.
                .logout((logout) -> logout
                        // logout url지정
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        // logout 성공시 url 지정
                        .logoutSuccessUrl("/").permitAll()
                        // 세션 무효화시키기
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            //OAuth2User defaultOAuth2User = (OAuth2User) authentication.getPrincipal();
 
            // var id = defaultOAuth2User.getAttributes().get("id");
            // var connected_at = defaultOAuth2User.getAttributes().get("connected_at");
            // var email = ((Map<String, Object>) defaultOAuth2User.getAttributes().get("kakao_account")).get("email");
            // var nickname = ((Map<String, Object>) defaultOAuth2User.getAttributes().get("properties")).get("nickname");

            // var ddd = 1;
            // String id = defaultOAuth2User.getAttributes().get("id").toString();
            // String body = """
            //         {"id":"%s"}
            //         """.formatted(id);
 
            // response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            // response.setCharacterEncoding(StandardCharsets.UTF_8.name());
 
            // PrintWriter writer = response.getWriter();
            // writer.println(body);
            // writer.flush();
        });
    }
}