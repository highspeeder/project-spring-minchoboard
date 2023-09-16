package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;

//@Repository : 생략 가능하다. JpaRepository는 Bean에 자동등록됨.
public interface BoardRepository extends JpaRepository<Board, Integer>{

}
