package com.ldu.blog.handler;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.dto.ResponseDto;

@ControllerAdvice // 모든 Exception 이 발생하면 이 클래스로 오도록 설정.
@RestController
public class GlobalExceptionHandler {

	// IllegalArgumentException에 대한 예외 처리를 하도록 어노테이션
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
	@ExceptionHandler(value = EmptyResultDataAccessException.class)
	public ResponseDto<String> handleArgumentException(EmptyResultDataAccessException e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
}
