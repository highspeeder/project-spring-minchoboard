package com.cos.blog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//getter, setter를 다 만들어줌.
@Data
//final에 대한 생성자를 만들어줌.
//@RequiredArgsConstructor
//모든 매개변수의 생성자를 만들어줌.
@AllArgsConstructor
//매개변수가 없느 생성자를 만들어줌.
@NoArgsConstructor
//빌더 패턴 적용
@Builder
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne //Reply = Many, Board = One: 한 게시글의 여러개의 댓글을 쓸 수 있다.
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne //Reply = Many, User = One: 한 유저가 여러개의 댓글을 쓸 수 있다.
    @JoinColumn(name="userId")
    private User user;

    @CreationTimestamp //insert될 때 자동으로 현재시간 입력.
    //@Column(name = "createDate", columnDefinition = "timestamp without time zone")
    private Timestamp createDate;
}
