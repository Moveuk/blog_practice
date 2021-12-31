package com.ldu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ldu.blog.model.User;

// DAO
// JpaRepository 상속으로 자동 bean 등록 되므로 @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {
	// JPA Naming 전략 - 메소드 이름을 보고 자동으로 다음과 같은 SELECT문을 날림.
	// SELECT * FROM user WHERE username=?1 AND password=?2;
	User findByUsernameAndPassword(String username, String password);
	
	// nativeQuery를 날리는 방법.
//	@Query(value = "SELECT * FROM user WHERE username=?1 AND password=?2", nativeQuery = true)
//	User login(String username, String password);
}
