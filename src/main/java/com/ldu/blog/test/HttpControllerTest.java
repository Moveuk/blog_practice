package com.ldu.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자의 요청 -> 응답(HTML 파일)
// @Controller

// 사용자의 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	// 접근 uri : http://localhost:8282/http/~~~
	
	private static final String TAG = "HttpControllerTest : ";

	@GetMapping("/http/lombok")
	public String lombokTest() {
		// lombok 어노테이션을 달아서 자동완성에 보임
		
		// @AllArgsConstructor
		Member m = Member.builder().username("testname").password("1234").email("testemail").build();
		System.out.println(TAG+"getter : "+m.getId());
		m.setId(5000);
		System.out.println(TAG+"setter : "+m.getId());
		// @NoArgsConstructor
		
		return "lombok test 완료";
	}
	
	@GetMapping("/http/get")
	public String getTest(Member m) { // (@RequestParam int id, @RequestParam String username) 
		
		return "get 요청 : "+m.getId()+", "+ m.getUsername()+", "+ m.getPassword()+", "+ m.getEmail();
	}

	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청 : "+m.getId()+", "+ m.getUsername()+", "+ m.getPassword()+", "+ m.getEmail();
	}

	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+m.getId()+", "+ m.getUsername()+", "+ m.getPassword()+", "+ m.getEmail();
	}

	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청 : ";
	}
}
