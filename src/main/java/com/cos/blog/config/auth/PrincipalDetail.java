package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import com.cos.blog.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

// spring security가 로그인 요청을 가로채서 로그인 진행하고 완료되면 UserDetails type의 오브젝트를 
// 시큐리티의 고유 세션 저장소에 저장한다. 이때 저장되는것이 PrincipalDetail
@Getter
public class PrincipalDetail implements UserDetails {
    
    private User user; // composition

    public PrincipalDetail(User user) {
        this.user = user;
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true; // non expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // non locked
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // credential non expired
    }
    
    @Override
    public boolean isEnabled() {
        return true; // 게정 활성화
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 계정의 권한

        Collection<GrantedAuthority> collection = new ArrayList<>();
        
        collection.add(() -> {
            return "ROLE_" + user.getRole();
        });

        return collection;
    }
}
