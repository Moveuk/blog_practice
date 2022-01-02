package com.ldu.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ldu.blog.model.User;
import com.ldu.blog.repository.UserRepository;

@Service // Bean 등록
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// 스프링이 로그인을 가로챌 때, username과 password 변수를 가로챔.
	// password 부분 처리는 알아서 처리하고,
	// username이 DB에 있는지를 이 함수에서 확인해주면 됨.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(() -> {
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : "+username);
				});
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 UserDetails 타입으로 저장됨.
		// principal을 안넣으면 아이디는 user, 패스워드는 콘솔창의 비밀번호 값이 기본값으로 설정된다.
	}

}
