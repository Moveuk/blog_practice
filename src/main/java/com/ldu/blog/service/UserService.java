package com.ldu.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldu.blog.model.User;
import com.ldu.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired	// SecurityConfig.java 에서 Bean 설정(IoC) 해두었으므로 DI 가능.
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int 회원가입(User user) {
		try {
			// 비밀번호 해쉬화
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);	// 해쉬값
			user.setPassword(encPassword);
			
			// 해쉬화된 비밀번호를 가진 User 객체 저장.
			userRepository.save(user);
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService : 회원가입()" + e.getMessage());
		}
		return -1;
	}

	@Transactional
	public int 회원수정(User requestUser) {
		// 영속화
		User user = userRepository.findById(requestUser.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패 : 아이디를 찾을 수 없습니다.");
		});
	
		String rawPassword = requestUser.getPassword();
		String encPassword = encoder.encode(rawPassword);	// 해쉬값
		
		user.setPassword(encPassword);
		user.setEmail(requestUser.getEmail());
		return 1;
	}

}
