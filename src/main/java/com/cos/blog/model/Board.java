package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//getter, setter를 다 만들어줌.
@Data
//모든 매개변수의 생성자를 만들어줌.
@AllArgsConstructor
//매개변수가 없는 생성자를 만들어줌.
@NoArgsConstructor
//빌더 패턴 적용
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title; //제목

    @Column(columnDefinition = "TEXT") //대용량 데이터
    private String content; //내용
    
    @ColumnDefault("0")
    private int count; //조회수

    @CreationTimestamp //insert될 때 자동으로 현재시간 입력.
    //@Column(name = "createDate", columnDefinition = "timestamp without time zone")
    private Timestamp createDate;

    @ManyToOne //Board = Many, User = One: 한 사용자가 여러개의 게시글을 쓸 수 있다.
    @JoinColumn(name = "userId")
    private User user; //DB에서는 FK를 사용하지만, 자바는 객체를 사용한다.

    //mappedBy 연관관계의 주인이 아니다. FK가 아니다. DB에 컬럼 만들지 마라.
    //cascade REMOVE - board행을 삭제할 때, 관련 Reply도 다 삭제한다.
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) 
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replies;
}
