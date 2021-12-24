package com.ldu.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.model.RoleType;
import com.ldu.blog.model.User;
import com.ldu.blog.repository.UserRepository;

@RestController
public class DummyContollerTest {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;

	// {id} 주소로 파라미터를 전달 받을 수 있음.
	// http://localhost:8282/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// Optional Type으로 리턴함
		// 이유 : 만약 잘못된 id를 넣어서 null이 리턴이 되면 안되므로
		// Optional 객체로 감싸서 가져온다음에 null 체크를 한 후 return 할 수 있도록 해주는 것.
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// 만약 User 객체가 아니면(null) 이면 빈 User 객체를 만들어 리턴하도록 설정.
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});

		// null 값과 대처 방법
		// 1. null이 아닌 값일 경우 단순히 get() 메소드를 사용하여 객체를 리턴 받으면 됨.
		// 2. null 일 가능성이 있는 경우 orElseGet() 메소드 사용 가능
		// 파라미터로 Supplier를 사용하며 get 메소드를 오버라이드 해서 사용
		/*
		 * orElseGet(new Supplier<User>() {
		 * 
		 * @Override public User get() { // 만약 User 객체가 아니면(null) 이면 빈 User 객체를 만들어
		 * 리턴하도록 설정. return new User(); }
		 */
		// 3. null 가능성 있을 경우 orElseThrow 메소드를 사용하여 Supplier에 exception throw
		// 4. 람다식 활용
		/*
		 User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
		});
		 */
		
		// return은 user 객체를 보내지만 MessageConverter가 Jackson 라이브러리를 사용해서 알아서 JSON 오브젝트로 변환해줌.
		return user;
	}

	@PostMapping("/dummy/join")
	public String join(User user) {
		// JPA가 String username, String password, String email 만으로도 받아줌
		// 하지만 더 강력한 기술로 Object 자체로도 받을 수 있음.
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());

		user.setRole(RoleType.USER); // RoleType 중에 User를 선택하여 넣어줌.
		System.out.println("role : " + user.getRole());
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
