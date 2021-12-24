package com.ldu.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ldu.blog.model.RoleType;
import com.ldu.blog.model.User;
import com.ldu.blog.repository.UserRepository;

@RestController
public class DummyContollerTest {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;

	// 1. save함수는 id 파라미터가 없으면 insert를 해주고
	// 2. save함수는 id 파라미터가 있어서 검색이 가능하고 id에 대한 정보들이 있을 때 update 해주고
	// 3. save함수는 id 파라미터가 있으서 검색이 가능하고 id에 대한 정보들이 없을 때는 insert를 해준다.
	// email, password
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());

		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다..");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		requestUser.setId(id);
		// userRepository.save(requestUser); // @Transactional 어노테이션에 의해서 save 함수가 없어도 자동 update가 된다.
		return null;
	}

	// http://localhost:8282/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}

	// http://localhost:8282/blog/dummy/user
	@GetMapping("/dummy/user")
	public List<User> pageList(
			@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);

		// Page 객체에 들어있는 isLast 와 같은 기능들을 사용하여 분기를 만들 수 도 있다.
		List<User> users = pagingUser.getContent();
		return users;
	}

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
		 * User user = userRepository.findById(id).orElseThrow(()->{ return new
		 * IllegalArgumentException("해당 유저는 없습니다. id : " + id); });
		 */

		// return은 user 객체를 보내지만 MessageConverter가 Jackson 라이브러리를 사용해서 알아서 JSON 오브젝트로
		// 변환해줌.
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
