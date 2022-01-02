package com.ldu.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ldu.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {

	@GetMapping({"","/"}) // 배열로 두가지 매핑 가능.
	public String index(@AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("로그인 사용자 아이디 : "+principal.getUsername());
		return "index"; // index.jsp로 이동
	}
}
