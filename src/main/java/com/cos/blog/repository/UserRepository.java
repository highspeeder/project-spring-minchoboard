package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//@Repository : 생략 가능하다. JpaRepository는 Bean에 자동등록됨.
public interface UserRepository extends JpaRepository<User, Integer>{

    // //select * from appuser WHERE username = ? AND password = ?;
    // User findByUsernameAndPassword(String username, String password);

    //select * from appuser WHERE username = ?;
    Optional<User> findByUsername(String username);
}
