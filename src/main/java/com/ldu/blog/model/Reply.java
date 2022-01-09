package com.ldu.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // auto_increment

	@Column(nullable = false, length = 100)
	private String content;

	@ManyToOne // Many(reply) to One(board)
	@JoinColumn(name = "boardId")
	private Board board;

	@ManyToOne // Many(reply) to One(user)
	@JoinColumn(name = "userId")
	private User user;

	@CreationTimestamp
	private Timestamp createDate;

	public void update(User user, Board board, String content) {
		setUser(user);
		setBoard(board);
		setContent(content);
	}
}
