package com.ldu.blog.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.config.auth.PrincipalDetail;
import com.ldu.blog.dto.ReplySaveRequestDto;
import com.ldu.blog.dto.ResponseDto;
import com.ldu.blog.model.Board;
import com.ldu.blog.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

	private final BoardService boardService;

	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("BoardApiController : save 호출됨");
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 데이터 받을 때는 컨트롤러에서 dto를 만들어서 받는게 좋다.
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto reply) {
		System.out.println("BoardApiController : replySave 호출됨");
		int result = boardService.댓글쓰기(reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}

	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		int result = boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}

	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		int result = boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
		int result = boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
}
