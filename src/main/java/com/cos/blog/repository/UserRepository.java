package com.cos.blog.repository;

import java.util.Optional;

import com.cos.blog.model.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    // SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username);
}

// JPA Naming Strategy
// SELECT * FROM user WHERE username = ? AND password = ?;
// User findByUsernameAndPassword(String username, String password);

// @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
// User login(String username, String password);