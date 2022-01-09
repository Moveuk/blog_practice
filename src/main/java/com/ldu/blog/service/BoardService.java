package com.ldu.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldu.blog.dto.ReplySaveRequestDto;
import com.ldu.blog.model.Board;
import com.ldu.blog.model.Reply;
import com.ldu.blog.model.User;
import com.ldu.blog.repository.BoardRepository;
import com.ldu.blog.repository.ReplyRepository;
import com.ldu.blog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired 
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public int 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board); // title, content
		return 1;
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public int 글삭제하기(int id) {
		boardRepository.deleteById(id);
		return 1;
	}

	@Transactional
	public int 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		}); // 영속성 컨텍스트에 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 서비스 종료시 트랜잭션 종료되면서 `더티체킹`이 일어나면서 db에 flush 함.
		return 1;
	}

	@Transactional
	public int 댓글쓰기(ReplySaveRequestDto reply) {
		User user = userRepository.findById(reply.getUserId()).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 실패 : 해당하는 User를 찾을 수 없습니다.");
		});
		Board board = boardRepository.findById(reply.getBoardId()).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
		}); // 영속화
		
		Reply requestReply = new Reply();
		requestReply.update(user, board, reply.getContent());
		
		replyRepository.save(requestReply);
		
		return 1;
	}

}
