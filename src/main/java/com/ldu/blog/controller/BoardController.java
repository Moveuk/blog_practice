package com.ldu.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping({"","/"}) // 배열로 두가지 매핑 가능.
	public String index() {
		return "index"; // index.jsp로 이동
	}
}
