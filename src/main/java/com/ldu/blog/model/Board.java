package com.ldu.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //auto_increment
    private int id;
    
    @Column(nullable =  false, length = 100)
    private String title;
    
    @Lob // 대용량 데이터 (Large OBject)
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.
    
    private int count; // 조회수
    
    @ManyToOne(fetch = FetchType.EAGER) // Many(Board) To One(User) - 한명의 유저에 의해서 여러 개의 게시글 작성 가능.
    // ManyToOne의 기본 fetch 전략은 각 하나만을 가져오므로 EAGER이다.
    @JoinColumn(name= "userId") // 테이블에는 userId 로 저장되도록 설정
    private User user; // 글쓴이 - DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
    
    // mappedBy - 연관관계의 주인이 아니므로 DB에 컬럼을 만들지 않도록 해줌.
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 여기서 board는 Reply VO에 field로 사용된 변수명이다.
    // OneToMany의 기본 fetch 전략은 LAZY이다.
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc") // javax.persistence.OrderBy -  내림차순으로 바꾸는 방법
    private List<Reply> replys; // JPA가 해당 게시글에 연결된 댓글을 모두 가져오면 여러개이므로 컬렉션으로 받아야함.
    
    @CreationTimestamp
    private Timestamp createDate;
}
