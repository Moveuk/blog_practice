package com.ldu.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldu.blog.model.Board;
import com.ldu.blog.model.User;
import com.ldu.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public int 글쓰기(Board board, User user) {
		try {
			board.setCount(0);
			board.setUser(user);
			boardRepository.save(board); // title, content
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService : 회원가입()" + e.getMessage());
		}
		return -1;
	}

	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
}
