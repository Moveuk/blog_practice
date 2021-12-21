package com.ldu.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
/*@Getter
@Setter*/
//@RequiredArgsConstructor // final 붙은 필드에 대해서만 생성자를 생성함.
@NoArgsConstructor // 빈생성자
public class Member {
	private /* final */ int id;
	private /* final */ String username;
	private /* final */ String password;
	private /* final */ String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
