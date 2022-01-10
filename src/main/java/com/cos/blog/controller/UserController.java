package com.cos.blog.controller;


import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


// 인증이 안된 사용자들이 출입할 수있는 경로를 /auth/* 허용
// 그냥 주소가 루프페이지이면 index.jsp 허용
// static 이하에있는 /js/* /css/* /image/* 모두 허용

@Controller
public class UserController {

    @Value("${kakao.key}")
    private String kakaoKey;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private String kakaoClientId = "d59efe44a0045236abf84265572b5e54";
    private String kakaoRedirectUrl = "http://localhost:8000/auth/kakao/callback";
    
    @GetMapping("/auth/join_form")
    public String joinForm() {
        return "user/join_form";
    }

    @GetMapping("/auth/login_form")
    public String loginForm() {
        return "user/login_form";
    }

    @GetMapping("/user/update_form")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
        return "user/update_form";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { // data return controller function

        // post 방식으로 key=value type data를 kakao에게 request해야 함
        RestTemplate rt = new RestTemplate();

        // HttpHeader object
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody object
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUrl);
        params.add("code", code);

        // HttpEntity and HttpBody put in 'kakaoTokenRequest' object
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
            new HttpEntity<MultiValueMap<String,String>>(params, headers);

        // Http request - Post method
        ResponseEntity<String> response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
        );

        // response handling
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            // kakao token
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 사용자 요청 가져오기
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader object
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpEntity and HttpBody put in 'kakaoTokenRequest' object
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
            new HttpEntity<>(headers2);

        // Http request - Post method
        ResponseEntity<String> profileResponse = rt2.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoProfileRequest,
            String.class
        );

        System.out.println(">>> "+profileResponse.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(profileResponse.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // kakao user object : username, password, email
        
        User kakaoUser = User.builder()
            .username(kakaoProfile.getProperties().getNickname())
            .password(kakaoKey)
            .email(kakaoProfile.getKakao_account().getEmail())
            .oauth("kakao")
            .build()
            ;

        // 가입자인지 비 가입자인지 체크하여 비가입자이면 회원가입, 가입자이면 로그인 처리
        // 가입자인지 확인하기 위해서는 id(username)으로 찾음
        User originUser = userService.findUser(kakaoUser.getUsername());

        if (originUser.getUsername() == null) { // 새로운 회원일 경우
            System.out.println("New User. Sign Up processing start.");
            userService.signUp(kakaoUser);
        }

        // login process
        System.out.println("Process Auto Login");
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoKey)); // 강제 로그인 처리
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return "redirect:/";
    }
    
}
