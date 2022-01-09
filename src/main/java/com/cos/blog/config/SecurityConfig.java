package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration // Bean 등록, IoC 관리
@EnableWebSecurity // security filter 추가
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근할 시, 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    // security 대신 로그인할 때 password를 가로채는데 해당 pw가 어떤 해쉬방법으로 암호화했는지 알아야 DB의 해쉬값과 비교할수있다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(principalDetailService)
            .passwordEncoder(encodePWD())
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // csrf token 비활성화
            .authorizeRequests()
                .antMatchers("/auth/**", "/js/**", "/css/**", "/image/**", "/").permitAll() // /auth/... permit all
                .anyRequest().authenticated() // 그게 아닌 모든 request는 인증필요
            .and()
                .formLogin()
                .loginPage("/auth/login_form")
                .loginProcessingUrl("/auth/login_proc") // spring security가 해당 url를 가로채서 로그인 프로세스 진행.
                .defaultSuccessUrl("/")
                // .failureUrl("/login_fail")
            ;
    }
}
