package com.ldu.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// 기본적으로 모든 요청은 Security가 요청을 가로채서 필터링이 됨.

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 설정
@Configuration	// 빈등록 (IoC 관리)
@EnableWebSecurity // 시큐리티 필터 추가(Default로 필터됨) -> 필터에 대한 설정을 이 클래스에서 정리함.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http); // configure에 anyRequest가 있어서 문제 생김.
		http
			.authorizeRequests()					// 다음과 같은 request가 들어오면 호출.
				.antMatchers("/auth/**")		// auth로 시작하는 모든 호출은
				.permitAll()								// 들어오는 것을 허가
				.anyRequest()							// 다른 요청은
				.authenticated()						// 인가 받아야함.
			.and()
				.formLogin()										// 위에서 다른 요청은 인가를 받아야 하므로 
				.loginPage("/auth/loginForm");	// 로그인 페이지로 보냄
	}

}
