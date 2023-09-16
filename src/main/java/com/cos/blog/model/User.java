package com.cos.blog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//final에 대한 생성자를 만들어줌.
//@RequiredArgsConstructor
//모든 매개변수의 생성자를 만들어줌.
//@AllArgsConstructor
//매개변수가 없는 생성자를 만들어줌.
//@NoArgsConstructor

//getter, setter를 다 만들어줌.
@Data
//모든 매개변수의 생성자를 만들어줌.
@AllArgsConstructor
//매개변수가 없는 생성자를 만들어줌.
@NoArgsConstructor
//빌더 패턴 적용
@Builder
//DB에 테이블을 만들어준다.
@Entity(name = "appuser")
//@DynamicInsert //insert할 때, 값이 null인 필드는 제외함.
public class User {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //연결된 DB의 넘버링 전략을 따라간다.
    private int id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp //insert될 때 자동으로 현재시간 입력.
    //@Column(name = "createDate", columnDefinition = "timestamp without time zone")
    private Timestamp createDate;
}
