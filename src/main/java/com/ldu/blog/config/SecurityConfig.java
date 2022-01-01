package com.ldu.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 기본적으로 모든 요청은 Security가 요청을 가로채서 필터링이 됨.

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 설정
@Configuration // 빈등록 (IoC 관리)
@EnableWebSecurity // 시큐리티 필터 추가(Default로 필터됨) -> 필터에 대한 설정을 이 클래스에서 정리함.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean // IoC 등록 : return 값을 스프링이 관리하도록 만듬. - 해쉬화 하는 함수를 리턴함.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http); // configure에 anyRequest가 있어서 문제 생김.
		http.csrf().disable() // csrf 토큰 비활성화 (테스트시 비활성화)
				.authorizeRequests() // 다음과 같은 request가 들어오면 호출.
				.antMatchers("/", "/auth/**") // auth로 시작하는 모든 호출은
				.permitAll() // 상위주소 요청은 인증절차 없이 사용을 허가
				.anyRequest() // 다른 요청은
				.authenticated() // 인가 받아야함.
				.and().formLogin() // 위에서 다른 요청은 인가를 받아야 하므로
				.loginPage("/auth/loginForm"); // 로그인 페이지로 보냄
	}

}
