package com.ldu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ldu.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}
