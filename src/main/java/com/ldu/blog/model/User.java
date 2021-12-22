package com.ldu.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
	
	@Id // pk 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB 넘버링 전략을 따라감.
	// Sequence(시퀀스 사용-오라클), Table(테이블의 번호를 맞춰서 사용), auto(자동) 등의 옵션이 있다.
	// IDENTITY -> 시퀀스 경우 오라클은 시퀀스, mysql은 auto_increment를 사용
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 30)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 123456 => 해쉬 (비밀번호 암호화를 위한 length 크기 설정)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@ColumnDefault("'user'") // user 디폴트 값으로 주며 String임을 알려주기 위하여 '' 사용
	private String role; // Enum을 쓰는게 좋음. (Enum을 쓰면 도메인(사용가능한 범위)을 만들어 줄수 있음. ex) admin, user, manager
	
	@CreationTimestamp // 시간이 자동 입력
	private Timestamp createDate;
}
