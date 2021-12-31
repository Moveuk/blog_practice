package com.ldu.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.dto.ResponseDto;
import com.ldu.blog.model.RoleType;
import com.ldu.blog.model.User;
import com.ldu.blog.service.UserService;

@RestController // 웹과 앱을 겸용할 수 있음.
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		// 실제 DB에 Insert를 위한 로직
		user.setRole(RoleType.USER);	// role 은 없으므로 수동으로 넣어줌
		int result = userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result); // 1 대신 dto에서 받아와서 넣음.
		// Java 객체를 messageConverter가 JSON으로 바꿔서 송신
		// user.js ajax 코드에서 응답을 위한 dataType을 JSON으로 적어 줬으므로 확인이됨.
		// dataType 적어주지 않더라도 json을 오브젝트로 자동 파싱해줌.
		
		/*
		 * ResponseDto 필드 값
		 int status; - http 통신 정상 성공 : HttpStatus.OK - 200
		  T data;
		 */
	}

	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		User principal = userService.로그인(user); // principal : 접근 주체
		
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
