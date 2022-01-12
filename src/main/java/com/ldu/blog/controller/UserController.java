package com.ldu.blog.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ldu.blog.model.User;
import com.ldu.blog.service.OAuthService;
import com.ldu.blog.service.UserService;

import lombok.RequiredArgsConstructor;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용 - 인증이 없어도 이용 가능한 페이지를 구분.
// 그냥 주소가 / 이면 index.jsp 이용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final OAuthService oAuthService;
	private final AuthenticationManager authenticationManager;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {// Data를 리턴해주는 컨트롤러 함수
		// 카카오 서버에 회원 프로파일 요청 후 사이트 User로 변경
		User kakaoUser = oAuthService.카카오로그인요청(code);
		
		// 이미 가입자인지 아닌지 체크해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		// 비가입자면 회원가입
		if(originUser.getUsername() == null) {
			System.out.println("새로운 회원 가입 실행");
			userService.회원가입(kakaoUser);
		}

		// 아니면 로그인 처리를 위한 세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication); // 시큐리티 컨텍스트에 authentication 등록
		
		return "redirect:/";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}

}
