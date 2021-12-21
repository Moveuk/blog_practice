package com.ldu.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일 리턴 기본 경로 : src/main/resources/static
		// 리턴명 : /home.html
		// 풀경로 : src/main/resources/static/home.html
		return "/home.html";
	} // application.yml 파일에서 mvc 서픽스 프리픽스를 없애면 반응이 된다.
	
	@GetMapping("/temp/img")
	public String tempImg() {
		System.out.println("tempImg()");
		return "/a.png";
	} // 정적 파일 브라우저에서 요청 가능
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		System.out.println("tempJsp()");
		// prefix : /WEB-INF/views/ - 실제 주소 : src/main/webapp/WEB-INF/views/
		// suffix : .jsp
		// 풀경로 : /WEB-INF/views/temp.jsp
		return "temp";
	} // jsp는 컴파일이 일어나야하는 동적 파일이므로 브라우저에서 요청 불가
	
}
