package com.ldu.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 Exception 이 발생하면 이 클래스로 오도록 설정.
@RestController
public class GlobalExceptionHandler {

	// IllegalArgumentException에 대한 예외 처리를 하도록 어노테이션
	@ExceptionHandler(value = IllegalArgumentException.class)
	public String handleArgumentException(IllegalArgumentException e) {
		return "<h1>"+e.getMessage()+"</h1>"; // String으로 전달되나 html로 받아들이는듯.(MIME이 text/html 일지도)
	}
	
}
