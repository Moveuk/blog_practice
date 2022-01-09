package com.ldu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ldu.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	// JPA 네이티브 쿼리
	@Modifying
	@Query(value = "INSERT INTO reply(userId, boardId, content, createDate) values(?1, ?2, ?3, now())", nativeQuery = true)
	 // dto의 필드 순서대로 ? 와일드 카드로 들어감.
	int myReplySave(int userId, int boardId, String content); // 업데이트된 행의 개수를 리턴해줌.
}
