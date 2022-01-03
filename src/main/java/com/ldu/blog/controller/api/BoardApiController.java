package com.ldu.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.config.auth.PrincipalDetail;
import com.ldu.blog.dto.ResponseDto;
import com.ldu.blog.model.Board;
import com.ldu.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("BoardApiController : save 호출됨");
		
		boardService.글쓰기(board, principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
