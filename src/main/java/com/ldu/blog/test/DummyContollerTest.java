package com.ldu.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.model.RoleType;
import com.ldu.blog.model.User;
import com.ldu.blog.repository.UserRepository;

@RestController
public class DummyContollerTest {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		// JPA가 String username, String password, String email 만으로도 받아줌
		// 하지만 더 강력한 기술로 Object 자체로도 받을 수 있음.
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		
		user.setRole(RoleType.USER); // RoleType 중에 User를 선택하여 넣어줌.
		System.out.println("role : " + user.getRole());
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
