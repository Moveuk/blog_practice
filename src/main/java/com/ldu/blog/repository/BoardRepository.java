package com.ldu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ldu.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
