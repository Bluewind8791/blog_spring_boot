package com.cos.blog.test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;

    // password email 받아서 update
    @Transactional // 함수 종료시에 자동 commit
    @PutMapping("/dummy/user/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User requestUser) {
        log.info("requestUser.getPassword : {}" + requestUser.getPassword());
        log.info("requestUser.getEmail : {}" + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        user.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));

        return "회원정보 수정이 완료되었습니다."+userRepository.findById(id);
    }
    // request json data -> Java object (messageConverter의 jackson 라이브러리가 변환하여 받음)


    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 없습니다. id : "+ id);
        });

        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    // 한 페이지 당 2건의 데이터를 return 받자
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> users = userPage.getContent();
        return users;
    }
    
    @PostMapping("/dummy/join")
    public String join(User user) {

        user.setRole(RoleType.USER);
        userRepository.save(user);

        log.info("\nuser : "+userRepository.findAll());
        log.info("회원가입이 완료되었습니다.");

        return "회원가입이 완료되었습니다.";
    }

    @DeleteMapping("/dummy/delete/{id}")
    public String delete(@PathVariable Long id) {
        userRepository.deleteById(id);

        return "<ID: "+id+"> 이 삭제되었습니다.";
    }
}
