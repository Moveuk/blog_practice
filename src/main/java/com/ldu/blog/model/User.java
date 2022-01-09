package com.ldu.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
// @DynamicInsert  // 계속 이런 어노테이션을 붙이다보면 너무 많이 늘어나게됨. -insert시 null인 필드 제외
public class User {
	
	@Id // pk 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB 넘버링 전략을 따라감.
	// Sequence(시퀀스 사용-오라클), Table(테이블의 번호를 맞춰서 사용), auto(자동) 등의 옵션이 있다.
	// IDENTITY -> 시퀀스 경우 오라클은 시퀀스, mysql은 auto_increment를 사용
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 123456 => 해쉬 (비밀번호 암호화를 위한 length 크기 설정)
	private String password;
	
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	//@ColumnDefault("'user'") // user 디폴트 값으로 주며 String임을 알려주기 위하여 '' 사용
	// DB에는 RoleType이 없으므로 String 객체임을 알려줘야함.
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋음. (Enum을 쓰면 도메인(사용가능한 범위)을 만들어 줄수 있음. ex) admin, user, manager
	
	// oAuth 로그인 사용자인지 확인용
	private String oauth; // kakao, google, null
	
	@CreationTimestamp // 시간이 자동 입력
	private Timestamp createDate;
}
