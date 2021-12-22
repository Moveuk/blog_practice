package com.ldu.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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
    
    @ColumnDefault("0")
    private int count; // 조회수
    
    @ManyToOne // Many(Board) To One(User) - 한명의 유저에 의해서 여러 개의 게시글 작성 가능.
    @JoinColumn(name= "userId") // 테이블에는 userId 로 저장되도록 설정
    private User user; // 글쓴이 - DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
    
    @CreationTimestamp
    private Timestamp createDate;
}
