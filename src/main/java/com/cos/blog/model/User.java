package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity // table 화 -> User 클래스가 MySQL에 테이블이 생성
@Builder // builder pattern
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // numbering 전략 : 프로젝트에서 연결된 DB의 넘버링 전략을 따른다.
    private Long id; // sequence, auto_increment

    @Column(nullable = false, length = 20, unique = true) // not null and 50 이하
    private String username;

    @Column(nullable = false, length = 100) // password는 hash화 하여 암호화해야하기때문에 넉넉하게
    private String password;
    
    @Column(nullable = false, length = 50)
    private String email;
    
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum 을 쓰는것이 좋다. -> admin, user, manager

    private String oauth; // kakao, google

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;
}

// @DynamicInsert -> Insert시에 null인 필드를 제외시켜준다
