package com.ldu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ldu.blog.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
