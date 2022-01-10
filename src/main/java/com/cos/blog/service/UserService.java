package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Transactional
    public void signUp(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserInfo(User user) {
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 user 오브젝트를 수정하는것이 좋다.
        // select를 해서 user object를 db로 부터 가져옴. (영속화)
        User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });

        // Validate check
        if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }

        // 회원수정 함수 종료 = 서비스 종료 = 트랜잭션 종료 -> update 문 commit 실행
    }

    @Transactional(readOnly = true)
    public User findUser(String username) {
        User user = userRepository.findByUsername(username).orElseGet(() -> {
            return new User();
        });
        return user;
    }

}
