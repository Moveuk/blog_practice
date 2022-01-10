package com.ldu.blog.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.dto.ResponseDto;
import com.ldu.blog.model.RoleType;
import com.ldu.blog.model.User;
import com.ldu.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController // 웹과 앱을 겸용할 수 있음.
@RequiredArgsConstructor
public class UserApiController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		// 실제 DB에 Insert를 위한 로직
		user.setRole(RoleType.USER);	// role 은 없으므로 수동으로 넣어줌
		int result = userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result); // 1 대신 dto에서 받아와서 넣음.
	}

	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		int result = userService.회원수정(user);
		// db 값 변경됨. but 클라이언트 측 세션값은 변경되지 않음.
		
		// 세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication); // 시큐리티 컨텍스트에 authentication 등록
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
	
}
