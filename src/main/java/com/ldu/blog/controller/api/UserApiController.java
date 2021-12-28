package com.ldu.blog.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.dto.ResponseDto;
import com.ldu.blog.model.User;

@RestController // 웹과 앱을 겸용할 수 있음.
public class UserApiController {
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		// 실제 DB에 Insert를 위한 로직
		
		return new ResponseDto<Integer>(HttpStatus.OK, 1); // 1 대신 dto에서 받아와서 넣음.
		// Java 객체를 messageConverter가 JSON으로 바꿔서 송신
		// user.js ajax 코드에서 응답을 위한 dataType을 JSON으로 적어 줬으므로 확인이됨.
		// dataType 적어주지 않더라도 json을 오브젝트로 자동 파싱해줌.
		
		/*
		 * ResponseDto 필드 값
		 int status; - http 통신 정상 성공 : HttpStatus.OK - 200
		  T data;
		 */
	}
}
