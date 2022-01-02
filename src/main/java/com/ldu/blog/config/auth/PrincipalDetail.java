package com.ldu.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ldu.blog.model.User;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다.
public class PrincipalDetail implements UserDetails {
	private User user; // 컴포지션
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정의 만료 사실을 리턴함. (true : 만료 안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정의 잠김 사실을 리턴함. (true : 잠김 안됨)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 계정 비밀번호의 만료 사실을 리턴함. (true : 만료 안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용가능) 상태인지 리턴함. (true : 활성화 상태)
	@Override
	public boolean isEnabled() {
		return true;
	}

	// 계정이 가진 권한 목록을 리턴함. (권한이 여러개 있다면 루프를 돌아야 함.)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collecters = new ArrayList<>();
		collecters.add(() -> {
			return "ROLE_" + user.getRole();
		}); // 시큐리티 문법 규칙 : prefix 'ROLE_'을 붙임

		return collecters;
	}
}
